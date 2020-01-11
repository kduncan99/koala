/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.InputPort;
import com.kadware.koala.ports.OutputPort;

public class AREnvelopeModule extends Module {

    public static final int GATE_PORT = 0;
    public static final int TRIGGER_PORT = 1;
    public static final int SIGNAL_PORT = 0;

    private double _attackTime = 0.0;           //  in msec
    private double _decrementPerSample = 0.0;   //  release mode
    private double _incrementPerSample = 0.0;   //  attack mode
    private boolean _gateOpen = false;
    private double _releaseTime = 0.0;          //  in msec
    private boolean _triggered = false;
    private double _value = Koala.MIN_PORT_VALUE;

    AREnvelopeModule() {
        _inputPorts.put(GATE_PORT, new InputPort("Gate"));
        _inputPorts.put(TRIGGER_PORT, new InputPort("Trigger"));
        _outputPorts.put(SIGNAL_PORT, new OutputPort("Output"));
        reset();
    }

    @Override
    public void advance() {
        boolean effectiveGate = _triggered || _gateOpen || _inputPorts.get(GATE_PORT).getValue() > 0;
        if (effectiveGate) {
            if (_value < Koala.MAX_PORT_VALUE) {
                _value += _incrementPerSample;
                if (_value >= Koala.MAX_PORT_VALUE) {
                    _value = Koala.MAX_PORT_VALUE;
                    _triggered = false;
                }
                _outputPorts.get(SIGNAL_PORT).setCurrentValue(_value);
            }
        } else {
            if (_value > Koala.MIN_PORT_VALUE) {
                _value -= _decrementPerSample;
                if (_value < Koala.MIN_PORT_VALUE) {
                    _value = Koala.MIN_PORT_VALUE;
                }
                _outputPorts.get(SIGNAL_PORT).setCurrentValue(_value);
            }
        }
    }

    @Override
    public void close() {}

    @Override
    public String getModuleClass() {
        return "AR Envelope Generator";
    }

    public double getAttackTime() {
        return _attackTime;
    }

    public double getReleaseTime() {
        return _releaseTime;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.AREnvelopeGenerator;
    }

    @Override
    public void reset() {
        _value = Koala.MIN_PORT_VALUE;
        _gateOpen = false;
    }

    public void setAttackTime(
        final double value
    ) {
        _attackTime = value;
        _incrementPerSample = (10000 / (Koala.SAMPLE_RATE * _attackTime)) - Koala.MIN_PORT_VALUE;
    }

    public void setGate(
        final boolean value
    ) {
        _gateOpen = value;
    }

    public void setTriggered() {
        _triggered = true;
    }

    public void setReleaseTime(
        final double value
    ) {
        _releaseTime = value;
        _decrementPerSample = (10000 / (Koala.SAMPLE_RATE * _releaseTime)) - Koala.MIN_PORT_VALUE;
    }
}
