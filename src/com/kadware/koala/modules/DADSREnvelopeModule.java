/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020-2023 by Kurt Duncan - All Rights Reserved
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

    private double _attackTime;      //  in msec
    private double _decayTime;       //  in msec
    private double _delayTime;       //  in msec
    private boolean _isInverted;
    private double _sustainLevel;    //  standard range
    private double _releaseTime;     //  in msec

    private int _stateCounter;
    private double _attackIncrement;
    private double _decayDecrement;
    private int _delaySamples;
    private boolean _gated;
    private boolean _manualGate;
    private boolean _manualTrigger;
    private double _releaseDecrement;
    private State _state;
    private boolean _triggered;
    private double _interimValue;

    DADSREnvelopeModule() {
        _trigger = new TriggerInputPort();
        _gate = new LogicInputPort();
        _output = new ContinuousOutputPort();

        _inputPorts.put(TRIGGER_INPUT_PORT, _trigger);
        _inputPorts.put(GATE_INPUT_PORT, _gate);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _output);

        _isInverted = false;

        setDelayTime(0.0f);
        setAttackTime(0.0f);
        setDecayTime(0.0f);
        setSustainLevel(Koala.POSITIVE_RANGE.getHighValue());
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
            case Dormant -> {
                if (gateRaised) {
                    establishState(State.Delay);
                }
            }
            case Delay -> {
                if (!_gated && !_triggered) {
                    establishState(State.Dormant);  //  we can go dormant since we haven't yet gone to attack
                } else if (_stateCounter == 0) {
                    establishState(State.Attack);
                } else {
                    --_stateCounter;
                }
            }
            case Attack -> {
                if (!_gated && !_triggered) {
                    establishState(State.Release);
                } else if (_output.getCurrentValue() >= Koala.POSITIVE_RANGE.getHighValue()) {
                    establishState(State.Decay);
                } else {
                    _interimValue = Koala.POSITIVE_RANGE.clipValue(_interimValue + _attackIncrement);
                    setOutputValue();
                }
            }
            case Decay -> {
                if (!_gated && !_triggered) {
                    establishState(State.Release);
                } else if (_stateCounter == 0) {
                    establishState(State.Sustain);
                } else {
                    _interimValue = Koala.POSITIVE_RANGE.clipValue(_interimValue - _decayDecrement);
                    setOutputValue();
                }
            }
            case Sustain -> {
                //  just stay here until the gate is released
                if (!_gated) {
                    establishState(State.Release);
                }
            }
            case Release -> {
                if (_stateCounter == 0) {
                    establishState(State.Dormant);
                } else {
                    _interimValue = Koala.POSITIVE_RANGE.clipValue(_interimValue - _releaseDecrement);
                    setOutputValue();
                }
            }
        }
    }

    @Override
    public void close() {}

    private void establishState(
        final State state
    ) {
        switch (state) {
            case Dormant:
                _interimValue = Koala.POSITIVE_RANGE.getLowValue();
                setOutputValue();
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

    public boolean isInverted() {
        return _isInverted;
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
        final double milliseconds
    ) {
        _attackTime = milliseconds;
        var samples = _attackTime * Koala.SAMPLE_RATE / 1000.0f;
        _attackIncrement = Koala.POSITIVE_RANGE.getDelta() / samples;
    }

    public void setDecayTime(
        final double milliseconds
    ) {
        _decayTime = milliseconds;
        var samples = _decayTime * Koala.SAMPLE_RATE / 1000.0f;
        _decayDecrement = (Koala.POSITIVE_RANGE.getHighValue() - _sustainLevel) / samples;
    }

    public void setDelayTime(
        final double milliseconds
    ) {
        _delayTime = milliseconds;
        _delaySamples = (int) (_delayTime * Koala.SAMPLE_RATE / 1000.0f);
    }

    public void setIsInverted(
        final boolean value
    ) {
        _isInverted = value;
    }

    public void setManualGate(
        final boolean value
    ) {
        _manualGate = value;
    }

    public void setReleaseTime(
        final double milliseconds
    ) {
        _releaseTime = milliseconds;
        var samples = _releaseTime * Koala.SAMPLE_RATE / 1000.0f;
        _releaseDecrement = (_sustainLevel - Koala.POSITIVE_RANGE.getLowValue()) / samples;
    }

    public void setSustainLevel(
        final double level
    ) {
        _sustainLevel = Koala.POSITIVE_RANGE.clipValue(level);
    }

    public void trigger() {
        _manualTrigger = true;
    }

    /**
     * Sets the output value according to _interimValue and the state of _isInverted
     */
    private void setOutputValue() {
        _output.setCurrentValue(_isInverted ? -_interimValue : _interimValue);
    }
}
