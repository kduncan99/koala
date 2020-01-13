/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
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

    public static enum State {
        Dormant,
        Delay,
        Attack,
        Decay,
        Sustain,
        Release,
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

    private int _delayCounter;
    private int _delaySamples;
    private boolean _gated;
    private boolean _manualGate;
    private boolean _manualTrigger;
    private State _state;
    private boolean _triggered;

    DADSREnvelopeModule() {
        _trigger = new TriggerInputPort("Trigger", "TRG");
        _gate = new LogicInputPort("Gate", "GTE");
        _output = new ContinuousOutputPort("Signal Output", "OUT");

        _inputPorts.put(TRIGGER_INPUT_PORT, _trigger);
        _inputPorts.put(GATE_INPUT_PORT, _gate);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _output);

        setDelayTime(0.0f);
        _attackTime = 0.0f;
        _decayTime = 0.0f;
        _sustainLevel = Koala.MAX_CVPORT_VALUE;
        _releaseTime = 0.0f;

        reset();
    }

    @Override
    public void advance(
    ) {
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
                    establishState(State.Dormant);
                } else if (_delayCounter == 0) {
                    establishState(State.Attack);
                } else {
                    --_delayCounter;
                }
                break;

            case Attack:
                //TODO

            case Decay:
                //TODO

            case Sustain:
                //TODO

            case Release:
                //TODO
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
                _delayCounter = _delaySamples;
                break;

            case Attack:
                //TODO

            case Decay:
                //TODO

            case Sustain:
                //TODO

            case Release:
                //TODO
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
    public String getModuleAbbreviation() {
        return "DADSR";
    }

    @Override
    public String getModuleClass() {
        return "Envelope Generator";
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

    public void trigger() {
        _manualTrigger = true;
    }
}
