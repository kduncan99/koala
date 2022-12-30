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

public class MonoOutputModule extends Module {

    public static final int SIGNAL_INPUT_PORT = 0;

    private static final int CHANNELS = 2;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = true;
    private static final AudioFormat AUDIO_FORMAT =
        new AudioFormat(Koala.SAMPLE_RATE, Koala.SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    private static final float SAMPLE_MAGNITUDE = (1 << (Koala.SAMPLE_SIZE_IN_BITS - 1)) - 1;

    private SourceDataLine _sourceDataLine = null;

    MonoOutputModule() {
        _inputPorts.put(SIGNAL_INPUT_PORT, new ContinuousInputPort("Input", "IN"));
        reset();
    }

    @Override
    public void advance() {
        ContinuousInputPort inp = (ContinuousInputPort) _inputPorts.get(SIGNAL_INPUT_PORT);
        int scaled = scale(inp.getValue());
        byte hibyte = (byte) (scaled >> 8);
        byte lobyte = (byte) scaled;
        byte[] buffer = {hibyte, lobyte, hibyte, lobyte};
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
        return "Mono Output";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.MonoOutput;
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
