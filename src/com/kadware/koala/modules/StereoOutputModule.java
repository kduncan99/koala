/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class StereoOutputModule extends Module {

    public static final int LEFT_SIGNAL_INPUT_PORT = 0;
    public static final int RIGHT_SIGNAL_INPUT_PORT = 1;

    private static final int CHANNELS = 2;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = true;
    private static final AudioFormat AUDIO_FORMAT =
        new AudioFormat((float) Koala.SAMPLE_RATE, Koala.SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    private static final double SAMPLE_MAGNITUDE = (1 << (Koala.SAMPLE_SIZE_IN_BITS - 1)) - 1;

    private SourceDataLine _sourceDataLine = null;
    private final ContinuousInputPort _leftInput;
    private final ContinuousInputPort _rightInput;

    private boolean _dimEnabled;
    private boolean _testToneEnabled;
    private final TestToneModule _testToneModule;
    private final ContinuousOutputPort _testToneOut;
    private final byte[] _buffer = new byte[4];

    StereoOutputModule() {
        _leftInput = new ContinuousInputPort();
        _rightInput = new ContinuousInputPort();
        _inputPorts.put(LEFT_SIGNAL_INPUT_PORT, _leftInput);
        _inputPorts.put(RIGHT_SIGNAL_INPUT_PORT, _rightInput);

        _dimEnabled = false;

        _testToneModule = new TestToneModule();
        _testToneOut = (ContinuousOutputPort) _testToneModule.getOutputPort(TestToneModule.SIGNAL_OUTPUT_PORT);
        _testToneEnabled = false;

        reset();
    }

    @Override
    public void advance() {
        _testToneModule.advance();

        var leftCombined = _leftInput.getValue();
        var rightCombined = _rightInput.getValue();
        if (_testToneEnabled) {
            leftCombined += _testToneOut.getCurrentValue();
            rightCombined += _testToneOut.getCurrentValue();
        }
        int leftScaled = scale(leftCombined);
        int rightScaled = scale(rightCombined);

        _buffer[0] = (byte) (leftScaled >> 8);
        _buffer[1] = (byte) leftScaled;
        _buffer[2] = (byte) (rightScaled >> 8);
        _buffer[3] = (byte) rightScaled;
        _sourceDataLine.write(_buffer, 0, 4);
    }

    @Override
    public void close() {
        _sourceDataLine.stop();
        _sourceDataLine.flush();
        _sourceDataLine.close();
        _sourceDataLine = null;
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.StereoOutput;
    }

    @Override
    public void reset() {
        _testToneModule.reset();
        if (_sourceDataLine != null) {
            close();
        }

        try {
            SourceDataLine sdl = AudioSystem.getSourceDataLine(AUDIO_FORMAT);
            sdl.open(AUDIO_FORMAT, 4096);
            sdl.start();
            _sourceDataLine = sdl;
        } catch (LineUnavailableException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void disableDim() {
        _dimEnabled = false;
    }

    public void disableTestTone() {
        _testToneEnabled = false;
    }

    public void enableDim() {
        _dimEnabled = true;
    }

    public void enableTestTone(
        final double frequency
    ) {
        _testToneModule.setBaseFrequency(frequency);
        _testToneEnabled = true;
    }

    public void toggleDim() {
        _dimEnabled = !_dimEnabled;
    }

    public void toggleTestTone() {
        _testToneEnabled = !_testToneEnabled;
    }

    public double getTestToneFrequency() { return _testToneModule.getBaseFrequency(); }
    public boolean isDimEnabled() { return _dimEnabled; }
    public boolean isTestToneEnabled() { return _testToneEnabled; }

    /**
     * Converts an input value scaled according to our port min/max, to a signed integer value
     * scaled according to our sample bit size.
     */
    private int scale(
        final double input
    ) {
        var result = input * SAMPLE_MAGNITUDE;
        if (_dimEnabled) {
            result *= 0.8;
        }
        return (int) result;
    }
}
