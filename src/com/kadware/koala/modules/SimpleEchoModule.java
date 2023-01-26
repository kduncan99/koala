/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2022,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

import java.util.Arrays;

public class SimpleEchoModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;
    public static final int SIGNAL_OUTPUT_PORT = 1;

    public final ContinuousInputPort _input;
    public final ContinuousOutputPort _output;

    private int _cacheIndex;
    private double _delayInMillis;
    private int _delayInSamples;
    private double[] _cache;

    SimpleEchoModule() {
        _input = new ContinuousInputPort();
        _output = new ContinuousOutputPort();
        _inputPorts.put(SIGNAL_INPUT_PORT, _input);
        _outputPorts.put(SIGNAL_OUTPUT_PORT, _output);

        setDelayInMillis(10);
    }

    @Override
    public void advance() {
        if (_delayInSamples == 0) {
            _output.setCurrentValue(_input.getValue());
        } else {
            _output.setCurrentValue(_cache[_cacheIndex]);
            _cache[_cacheIndex] = _input.getValue();
            _cacheIndex++;
            if (_cacheIndex == _delayInSamples) {
                _cacheIndex = 0;
            }
        }
    }

    @Override
    public void close() {}

    @Override
    public ModuleType getModuleType() {
        return ModuleType.SimpleEcho;
    }

    @Override
    public void reset() {}

    public double getDelayInMillis() {
        return _delayInMillis;
    }

    public void setDelayInMillis(
        final double milliseconds
    ) {
        _delayInMillis = milliseconds;
        _delayInSamples = (int) (_delayInMillis * Koala.SAMPLE_RATE / 1000.0);
        if (_delayInSamples == 0) {
            _cache = null;
            _cacheIndex = 0;
        } else {
            if (_cache == null) {
                _cache = new double[_delayInSamples];
                _cacheIndex = 0;
            } else {
                _cache = Arrays.copyOf(_cache, _delayInSamples);
                if (_cacheIndex >= _delayInSamples) {
                    _cacheIndex = _delayInSamples - 1;
                }
            }
        }
    }
}
