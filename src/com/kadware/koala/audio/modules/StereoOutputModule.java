/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.audio.components.DBFSComponent;
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

    private final SimpleOscillatorModule _oscillatorModule;
    private final DBFSComponent _leftDBFSComponent;
    private final DBFSComponent _rightDBFSComponent;
    private final ContinuousOutputPort _testToneOut;
    private final byte[] _buffer = new byte[4];

    StereoOutputModule() {
        _leftInput = new ContinuousInputPort();
        _rightInput = new ContinuousInputPort();
        _inputPorts.put(LEFT_SIGNAL_INPUT_PORT, _leftInput);
        _inputPorts.put(RIGHT_SIGNAL_INPUT_PORT, _rightInput);

        _dimEnabled = false;

        _oscillatorModule = new SimpleOscillatorModule();
        _testToneOut = (ContinuousOutputPort) _oscillatorModule.getOutputPort(SimpleOscillatorModule.OUTPUT_PORT);

        _leftDBFSComponent = new DBFSComponent();
        _rightDBFSComponent = new DBFSComponent();

        _testToneEnabled = false;

        reset();
    }

    @Override
    public void advance() {
        _oscillatorModule.advance();

        var leftCombined = _leftInput.getValue();
        var rightCombined = _rightInput.getValue();
        if (_testToneEnabled) {
            leftCombined += _testToneOut.getCurrentValue();
            rightCombined += _testToneOut.getCurrentValue();
        }

        if (_dimEnabled) {
            leftCombined /= 2;
            rightCombined /= 2;
        }

        _leftDBFSComponent.inject(leftCombined);
        _rightDBFSComponent.inject(rightCombined);

        //  convert scaled values to system values (i.e., -1.0 <= n <= 1.0 --> -32767 <= n <= 32767
        int leftSystemScaled = (int)(leftCombined * SAMPLE_MAGNITUDE);
        int rightSystemScaled = (int)(rightCombined * SAMPLE_MAGNITUDE);
        _buffer[0] = (byte) (leftSystemScaled >> 8);
        _buffer[1] = (byte) leftSystemScaled;
        _buffer[2] = (byte) (rightSystemScaled >> 8);
        _buffer[3] = (byte) rightSystemScaled;
        _sourceDataLine.write(_buffer, 0, 4);
    }

    @Override
    public void close() {
        _leftDBFSComponent.close();
        _rightDBFSComponent.close();

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
        _oscillatorModule.reset();
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

        _leftDBFSComponent.reset();
        _rightDBFSComponent.reset();
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
        _oscillatorModule.setBaseFrequency(frequency);
        _testToneEnabled = true;
    }

    public DBFSComponent getLeftDBFSComponent() { return _leftDBFSComponent; }
    public DBFSComponent getRightDBFSComponent() { return _rightDBFSComponent; }

    public void toggleDim() {
        _dimEnabled = !_dimEnabled;
    }

    public void toggleTestTone() {
        _testToneEnabled = !_testToneEnabled;
    }

    public double getTestToneFrequency() { return _oscillatorModule.getBaseFrequency(); }
    public boolean isDimEnabled() { return _dimEnabled; }
    public boolean isTestToneEnabled() { return _testToneEnabled; }
}
