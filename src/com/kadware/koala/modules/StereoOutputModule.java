/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

@SuppressWarnings("Duplicates")
public class StereoOutputModule extends Module {

    public static final int LEFT_SIGNAL_INPUT_PORT = 0;
    public static final int RIGHT_SIGNAL_INPUT_PORT = 1;

    private static final int CHANNELS = 2;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = true;
    private static final AudioFormat AUDIO_FORMAT =
        new AudioFormat(Koala.SAMPLE_RATE, Koala.SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    private static final double SAMPLE_MAGNITUDE = (1 << (Koala.SAMPLE_SIZE_IN_BITS - 1)) - 1;

    private SourceDataLine _sourceDataLine = null;
    private final ContinuousInputPort _leftInput;
    private final ContinuousInputPort _rightInput;

    StereoOutputModule() {
        _leftInput = new ContinuousInputPort("Left Input", "LFT");
        _rightInput = new ContinuousInputPort("Right Input", "RGT");
        _inputPorts.put(LEFT_SIGNAL_INPUT_PORT, _leftInput);
        _inputPorts.put(RIGHT_SIGNAL_INPUT_PORT, _rightInput);
        reset();
    }

    @Override
    public void advance(
    ) {
        int leftScaled = scale(_leftInput.getValue());
        int rightScaled = scale(_rightInput.getValue());

        byte[] buffer = {(byte) (leftScaled >> 8),
                         (byte) leftScaled,
                         (byte) (rightScaled >> 8),
                         (byte) rightScaled};
        _sourceDataLine.write(buffer, 0, 4);
    }

    @Override
    public void close() {
        _sourceDataLine.stop();
        _sourceDataLine.flush();
        _sourceDataLine.close();
        _sourceDataLine = null;
    }

    @Override
    public String getModuleAbbreviation() {
        return "OUT";
    }

    @Override
    public String getModuleClass() {
        return "Stereo Output";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.StereoOutput;
    }

    @Override
    public void reset() {
        if (_sourceDataLine != null) {
            close();
        }

        try {
            SourceDataLine sdl = AudioSystem.getSourceDataLine(AUDIO_FORMAT);
            sdl.open();
            sdl.start();
            _sourceDataLine = sdl;
        } catch (LineUnavailableException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Converts an input value scaled according to our port min/max, to a signed integer value
     * scaled according to our sample bit size.
     */
    private int scale(
        final float input
    ) {
        //  adjust the input value which varies from MIN_PORT_VALUE to MAX_PORT_VALUE,
        //  such that it fits nicely within the SAMPLE_SIZE_IN_BITS range.
        return (int) (input * SAMPLE_MAGNITUDE / Koala.CVPORT_VALUE_RANGE);
    }
}
