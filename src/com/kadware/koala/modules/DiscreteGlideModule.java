/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.DiscreteInputPort;
import com.kadware.koala.ports.DiscreteOutputPort;

public class DiscreteGlideModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int SIGNAL_OUTPUT_PORT = 1;

    private double _glideTime = 0.0f;        //  in milliseconds
    private boolean _gliding = false;
    private double _incrementValue = 0.0f;
    private double _laggingValue = 0.0f;     //  floating point, because we might increment in very small amounts
    private int _targetValue = 0;

    DiscreteGlideModule() {
        _inputPorts.put(SIGNAL_INPUT_PORT, new DiscreteInputPort());
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new DiscreteOutputPort());
        reset();
    }

    @Override
    public void advance() {
        DiscreteInputPort inp = (DiscreteInputPort) _inputPorts.get(SIGNAL_INPUT_PORT);
        int inpValue = inp.getValue();
        if (inpValue != _targetValue) {
            //  Input value has changed since the last time we were here. Set up a new glide.
            _targetValue = inpValue;
            if (_glideTime <= 0) {
                _laggingValue = _targetValue;
                _incrementValue = 0.0f;
                _gliding = false;
            } else {
                double seconds = _glideTime / 1000.0f;
                _incrementValue = (_targetValue - _laggingValue) / (Koala.SAMPLE_RATE * seconds);
                _gliding = true;
            }
        }

        if (_gliding) {
            double diff = Math.abs(_laggingValue - _targetValue);
            if (diff < Math.abs(_incrementValue)) {
                _laggingValue = _targetValue;
                _gliding = false;
            } else {
                _laggingValue += _incrementValue;
            }
        }

        DiscreteOutputPort outp = (DiscreteOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        outp.setCurrentValue((int) _laggingValue);
    }

    @Override
    public void close() {}

    public double getGlideTime() {
        return _glideTime;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.DiscreteGlide;
    }

    @Override
    public void reset() {
        super.reset();
        _gliding = false;
        DiscreteInputPort inp = (DiscreteInputPort) _inputPorts.get(SIGNAL_INPUT_PORT);
        DiscreteOutputPort outp = (DiscreteOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        _targetValue = inp.getValue();
        _laggingValue = _targetValue;
        _incrementValue = 0.0f;
        outp.setCurrentValue(_targetValue);
    }

    public void setGlideTime(
        final double milliseconds
    ) {
        _glideTime = milliseconds;
    }
}
