/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala;

import com.kadware.koala.modules.*;
import com.kadware.koala.modules.Module;
import com.kadware.koala.ports.InputPort;
import com.kadware.koala.ports.OutputPort;
import com.kadware.koala.waves.Wave;
import com.kadware.koala.waves.WaveManager;

public class Koala {

    public static final float SAMPLE_RATE = 44100.0f;
    public static final int SAMPLE_SIZE_IN_BITS = 16;
    public static final double MAX_PORT_MAGNITUDE = 5.0;
    public static final double MAX_PORT_VALUE = MAX_PORT_MAGNITUDE;
    public static final double MIN_PORT_VALUE = 0 - MAX_PORT_MAGNITUDE;
    public static final double MILLISECONDS_PER_SAMPLE = 1000.0d / SAMPLE_RATE;

    public static void main(
        final String args[]
    ) throws Exception {
        ModuleManager.start();

        //  sounds wrong - we keep getting an audible glitch every LFO cycle... why?
        OscillatorModule lfoModule = (OscillatorModule) ModuleManager.createModule(Module.ModuleType.Oscillator);
        lfoModule.setWave(WaveManager.createWave(Wave.WaveType.Sine));
        lfoModule.setBaseFrequency(1.0d);
        OutputPort lfoOutput = lfoModule.getOutputPort(OscillatorModule.OUTPUT_PORT);

        OscillatorModule oscModule = (OscillatorModule) ModuleManager.createModule(Module.ModuleType.Oscillator);
        oscModule.setWave(WaveManager.createWave(Wave.WaveType.Square));
        OutputPort oscOutput = oscModule.getOutputPort(TestToneModule.OUTPUT_PORT);
        InputPort oscControl = oscModule.getInputPort(OscillatorModule.PULSE_WIDTH_PORT);
        oscControl.connectTo(lfoOutput);
        oscControl.setMultiplier(0.8d);

        AmplifierModule ampModule = (AmplifierModule) ModuleManager.createModule(Module.ModuleType.Amplifier);
        InputPort ampInput = ampModule.getInputPort(AmplifierModule.INPUT_PORT);
        OutputPort ampOutput = ampModule.getOutputPort(AmplifierModule.OUTPUT_PORT);
        ampModule.setBaseValue(2.0d);
        ampInput.connectTo(oscOutput);

        Module outModule = ModuleManager.createModule(Module.ModuleType.MonoOutput);
        InputPort outInput = outModule.getInputPort(MonoOutputModule.INPUT_PORT);
        outInput.connectTo(ampOutput);

        Thread.sleep(5000);

        ModuleManager.clear();
        ModuleManager.stop();
    }
}
