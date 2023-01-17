/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2022 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.LogicInputPort;
import com.kadware.koala.ports.TriggerInputPort;

/**
 * Implementation of DADSR envelope generator
 */
public class DADSREnvelopeModule extends Module {

    public enum State {
        Dormant,    //  pre-trigger, or post-release
        Delay,      //  trigger takes us to this state
        Attack,     //  this state is established after the delay period, while gate is still asserted
        Decay,      //  this state is established after the attack completes, and while gate is still asserted
        Sustain,    //  this state is established after the decay completes, and while gate is still asserted
        Release,    //  this state is established when gate transitions from asserted to not asserted
    }

    public static final int TRIGGER_INPUT_PORT = 0;
    public static final int GATE_INPUT_PORT = 1;
    public static final int SIGNAL_OUTPUT_PORT = 2;

    public final TriggerInputPort _trigger;
    public final LogicInputPort _gate;
    public final ContinuousOutputPort _output;

    private float _delayTime;       //  in msec
    private float _attackTime;      //  in msec
    private float _decayTime;       //  in msec
    private float _sustainLevel;    //  standard range
    private float _releaseTime;     //  in msec

    private int _stateCounter;
    private float _attackIncrement;
    private float _decayDecrement;
    private int _delaySamples;
    private boolean _gated;
    private boolean _manualGate;
    private boolean _manualTrigger;
    private float _releaseDecrement;
    private State _state;
    private boolean _triggered;

    DADSREnvelopeModule() {
        _trigger = new TriggerInputPort();
        _gate = new LogicInputPort();
        _output = new ContinuousOutputPort();

        _inputPorts.put(TRIGGER_INPUT_PORT, _trigger);
        _inputPorts.put(GATE_INPUT_PORT, _gate);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _output);

        setDelayTime(0.0f);
        setAttackTime(0.0f);
        setDecayTime(0.0f);
        setSustainLevel(Koala.MAX_CVPORT_VALUE);
        setReleaseTime(0.0f);

        reset();
    }

    @Override
    public void advance() {
        _triggered = _trigger.getValue() || _manualTrigger;
        boolean gateRaised = !_gated && (_gate.getValue() || _manualGate);
        _gated = gateRaised;

        if (_triggered) {
            _manualTrigger = false;
            establishState(State.Delay);
        }

        switch (_state) {
            case Dormant:
                if (gateRaised) {
                    establishState(State.Delay);
                }
                break;

            case Delay:
                if (!_gated && !_triggered) {
                    establishState(State.Dormant);  //  we can go dormant since we haven't yet gone to attack
                } else if (_stateCounter == 0) {
                    establishState(State.Attack);
                } else {
                    --_stateCounter;
                }
                break;

            case Attack:
                if (!_gated && !_triggered) {
                    establishState(State.Release);
                } else if (_output.getCurrentValue() >= Koala.MAX_CVPORT_VALUE) {
                    establishState(State.Decay);
                } else {
                    _output.setCurrentValue(_output.getCurrentValue() + _attackIncrement);
                }
                break;

            case Decay:
                if (!_gated && !_triggered) {
                    establishState(State.Release);
                } else if (_stateCounter == 0) {
                    establishState(State.Sustain);
                } else {
                    _output.setCurrentValue(_output.getCurrentValue() - _decayDecrement);
                }
                break;

            case Sustain:
                //  just stay here until the gate is released
                if (!_gated) {
                    establishState(State.Release);
                }
                break;

            case Release:
                if (_stateCounter == 0) {
                    establishState(State.Dormant);
                } else {
                    _output.setCurrentValue(_output.getCurrentValue() - _releaseDecrement);
                }
                break;
        }
    }

    @Override
    public void close() {}

    private void establishState(
        final State state
    ) {
        switch (state) {
            case Dormant:
                _output.setCurrentValue(Koala.MIN_CVPORT_VALUE);
                _gated = false;
                _triggered = false;
                break;

            case Delay:
                _stateCounter = _delaySamples;
                break;

            case Attack:
            case Decay:
            case Sustain:
            case Release:
                break;
        }

        _state = state;
    }

    public boolean getManualGate() {
        return _manualGate;
    }

    public boolean getManualTrigger() {
        return _manualTrigger;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.DADSREnvelopeGenerator;
    }

    public State getState() {
        return _state;
    }

    public boolean getTriggered() {
        return _triggered;
    }

    @Override
    public void reset() {
        super.reset();
        _manualGate = false;
        _manualTrigger = false;
        _gated = false;
        _triggered = false;
        establishState(State.Dormant);
    }

    public void setAttackTime(
        final float milliseconds
    ) {
        _attackTime = milliseconds;
        var samples = _attackTime * Koala.SAMPLE_RATE / 1000.0f;
        _attackIncrement = (Koala.MAX_CVPORT_VALUE - Koala.MIN_CVPORT_VALUE) / samples;
    }

    public void setDecayTime(
        final float milliseconds
    ) {
        _decayTime = milliseconds;
        var samples = _decayTime * Koala.SAMPLE_RATE / 1000.0f;
        _decayDecrement = (Koala.MAX_CVPORT_VALUE - _sustainLevel) / samples;
    }

    public void setDelayTime(
        final float milliseconds
    ) {
        _delayTime = milliseconds;
        _delaySamples = (int) (_delayTime * Koala.SAMPLE_RATE / 1000.0f);
    }

    public void setManualGate(
        final boolean value
    ) {
        _manualGate = value;
    }

    public void setReleaseTime(
        final float milliseconds
    ) {
        _releaseTime = milliseconds;
        var samples = _releaseTime * Koala.SAMPLE_RATE / 1000.0f;
        _releaseDecrement = (_sustainLevel - Koala.MIN_CVPORT_VALUE) / samples;
    }

    public void setSustainLevel(
        final float level
    ) {
        _sustainLevel = Math.min(Math.max(level, Koala.MIN_CVPORT_VALUE), Koala.MAX_CVPORT_VALUE);
    }

    public void trigger() {
        _manualTrigger = true;
    }
}
