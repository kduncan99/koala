/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala;

import com.kadware.koala.modules.*;
import com.kadware.koala.modules.Module;
import com.kadware.koala.ports.*;
import com.kadware.koala.waves.IWave;
import com.kadware.koala.waves.WaveManager;

public class Koala {

    public static final float SAMPLE_RATE = 44100.0f;
    public static final int SAMPLE_SIZE_IN_BITS = 16;
    public static final float MAX_CVPORT_VALUE = 5.0f;
    public static final float MIN_CVPORT_VALUE = -5.0f;
    public static final float CVPORT_VALUE_RANGE = MAX_CVPORT_VALUE - MIN_CVPORT_VALUE;

    //  Note frequencies for C4 up through B4 - divide or multiple for other octaves
    private static final float NF_C = 261.63f;
    private static final float NF_CS = 277.18f;
    private static final float NF_D = 293.66f;
    private static final float NF_DS = 311.13f;
    private static final float NF_E = 329.63f;
    private static final float NF_F = 349.23f;
    private static final float NF_FS = 349.23f;
    private static final float NF_G = 392.00f;
    private static final float NF_GS = 415.30f;
    private static final float NF_A = 440f;
    private static final float NF_AS = 466.16f;
    private static final float NF_B = 493.88f;

    public static void main(
        final String args[]
    ) throws Exception {
        ModuleManager.start();

        //  controller modules
        ClockModule clock = (ClockModule) ModuleManager.createModule(Module.ModuleType.Clock);
        clock.setBaseFrequency(4.0f);
        LogicOutputPort clockOutput = (LogicOutputPort) clock.getOutputPort(ClockModule.SIGNAL_OUTPUT_PORT);

        AREnvelopeModule env = (AREnvelopeModule) ModuleManager.createModule(Module.ModuleType.AREnvelopeGenerator);
        env.setAttackTime(0);
        env.setReleaseTime(300);
        LogicInputPort envTrigger = (LogicInputPort) env.getInputPort(AREnvelopeModule.TRIGGER_INPUT_PORT);
        ContinuousOutputPort envOutput = (ContinuousOutputPort) env.getOutputPort(AREnvelopeModule.SIGNAL_OUTPUT_PORT);

        int[] sequence = {(int) (NF_C * 1000),
                          (int) (NF_C * 1000),
                          (int) (NF_G * 1000),
                          (int) (NF_DS * 1000),
                          (int) (NF_AS * 1000),
                          (int) (NF_F * 1000),
                          (int) (NF_G * 1000),
                          (int) (NF_C * 2 * 1000)};
        DiscreteSequencerModule seq = (DiscreteSequencerModule) ModuleManager.createModule(Module.ModuleType.DiscreteSequencer);
        seq.setValues(sequence);
        LogicInputPort seqTrigger = (LogicInputPort) seq.getInputPort(DiscreteSequencerModule.TRIGGER_INPUT_PORT);
        DiscreteOutputPort seqSignal = (DiscreteOutputPort) seq.getOutputPort(DiscreteSequencerModule.SIGNAL_OUTPUT_PORT);

        DiscreteGlideModule glide = (DiscreteGlideModule) ModuleManager.createModule(Module.ModuleType.DiscreteGlide);
        glide.setGlideTime(50.0f);
        DiscreteInputPort glideIn = (DiscreteInputPort) glide.getInputPort(DiscreteGlideModule.SIGNAL_INPUT_PORT);
        DiscreteOutputPort glideOutput = (DiscreteOutputPort) glide.getOutputPort(DiscreteGlideModule.SIGNAL_OUTPUT_PORT);

        //  audio modules
        OscillatorModule vco = (OscillatorModule) ModuleManager.createModule(Module.ModuleType.Oscillator);
        vco.setWave(WaveManager.createWave(IWave.WaveType.Triangle));
        vco.setBaseFrequency(0f);
        DiscreteInputPort vcoFreqIn = (DiscreteInputPort) vco.getInputPort(OscillatorModule.FREQUENCY_INPUT_PORT);
        ContinuousOutputPort vcoOutput = (ContinuousOutputPort) vco.getOutputPort(OscillatorModule.OUTPUT_PORT);

        AmplifierModule vca = (AmplifierModule) ModuleManager.createModule(Module.ModuleType.Amplifier);
        vca.setBaseValue(0.0f);
        ContinuousInputPort vcaInput = (ContinuousInputPort) vca.getInputPort(AmplifierModule.SIGNAL_INPUT_PORT);
        ContinuousInputPort vcaControl = (ContinuousInputPort) vca.getInputPort(AmplifierModule.CONTROL_INPUT_PORT_1);
        ContinuousOutputPort vcaOutput = (ContinuousOutputPort) vca.getOutputPort(AmplifierModule.SIGNAL_OUTPUT_PORT);

        Module master = ModuleManager.createModule(Module.ModuleType.MonoOutput);
        ContinuousInputPort masterInput = (ContinuousInputPort) master.getInputPort(MonoOutputModule.SIGNAL_INPUT_PORT);

        //  connections
        seqTrigger.connectTo(clockOutput);
        envTrigger.connectTo(clockOutput);
        glideIn.connectTo(seqSignal);
        vcoFreqIn.connectTo(glideOutput);
        vcaControl.connectTo(envOutput);
        vcaInput.connectTo(vcoOutput);
        masterInput.connectTo(vcaOutput);

        Thread.sleep(10000);

        ModuleManager.clear();
        ModuleManager.stop();
    }
}
