/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class ModuleManager {

    private static final Set<Module> _modules = new HashSet<>();
    private static AdvanceThread _advanceThread = new AdvanceThread();

    private static class AdvanceThread extends Thread {

        private boolean _flag = false;

        public void run() {
            long nanosPerSample = (long) (1000000000.0d / Koala.SAMPLE_RATE);
            Instant now = Instant.now();
            Instant next = now.plusNanos(nanosPerSample);
            while (!_flag) {
                ModuleManager.advanceAll();
                long nanosToWait = Instant.now().until(next, ChronoUnit.NANOS);
                if (nanosToWait > 0) {
                    long millis = nanosToWait / 1000000;
                    int nanos = (int) (nanosToWait - (millis * 1000000));
                    try {
                        Thread.sleep(millis, nanos);
                    } catch (InterruptedException ex) {
                        //  do nothing
                    }
                }
            }
        }

        public void terminate() {
            _flag = true;
        }
    }

    private static synchronized void advanceAll() {
        for (Module module : _modules) {
            module.advance();
        }
    }

    public static synchronized void clear() {
        for (Module module : _modules) {
            module.detachAllPorts();
            module.close();
        }

        _modules.clear();
    }

    public static synchronized Module createModule(
        final Module.ModuleType moduleType
    ) {
        Module module = null;
        switch (moduleType) {
            case AREnvelopeGenerator:
                module = new AREnvelopeModule();
                break;
            case Clock:
                module = new ClockModule();
                break;
            case DADSREnvelopeGenerator:
                module = new DADSREnvelopeModule();
                break;
            case DiscreteGlide:
                module = new DiscreteGlideModule();
                break;
            case DiscreteSequencer:
                module = new DiscreteSequencerModule();
                break;
            case DualNoise:
                module = new DualNoiseModule();
                break;
            case FixedAmplifier:
                module = new FixedAmplifierModule();
                break;
            case FixedMixer:
                module = new FixedMixerModule();
                break;
            case FixedPanner:
                module = new FixedPanningModule();
                break;
            case Inverter:
                module = new InverterModule();
                break;
            case MonoOutput:
                module = new MonoOutputModule();
                break;
            case Noise:
                module = new NoiseModule();
                break;
            case StereoOutput:
                module = new StereoOutputModule();
                break;
            case TestTone:
                module = new TestToneModule();
                break;
            case VCAmplifier:
                module = new VCAmplifierModule();
                break;
            case VCFilter:
                module = new VCFilterModule();
                break;
            case VCMixer:
                module = new VCMixerModule();
                break;
            case VCOscillator:
                module = new VCOscillatorModule();
                break;
            case VCPanner:
                module = new VCPanningModule();
                break;
        }

        _modules.add(module);
        return module;
    }

    public static void start() {
        if (_advanceThread != null) {
            _advanceThread.terminate();
        }

        _advanceThread = new AdvanceThread();
        _advanceThread.start();
    }

    public static void stop() {
        if (_advanceThread != null) {
            _advanceThread.terminate();
            _advanceThread = null;
        }
    }
}
