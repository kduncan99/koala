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
        Module module = switch (moduleType) {
            case AREnvelopeGenerator -> new AREnvelopeModule();
            case Clock -> new ClockModule();
            case DADSREnvelopeGenerator -> new DADSREnvelopeModule();
            case DiscreteGlide -> new DiscreteGlideModule();
            case DiscreteSequencer -> new DiscreteSequencerModule();
            case DualNoise -> new DualNoiseModule();
            case FixedAmplifier -> new FixedAmplifierModule();
            case FixedMixer -> new FixedMixerModule();
            case FixedPanner -> new FixedPanningModule();
            case Inverter -> new InverterModule();
            case MonoOutput -> new MonoOutputModule();
            case Noise -> new NoiseModule();
            case SimpleEcho -> new SimpleEchoModule();
            case SimpleLFO -> new SimpleLFOModule();
            case StereoOutput -> new StereoOutputModule();
            case TestTone -> new TestToneModule();
            case VCAmplifier -> new VCAmplifierModule();
            case VCFilter -> new VCFilterModule();
            case VCMixer -> new VCMixerModule();
            case VCOscillator -> new VCOscillatorModule();
            case VCPanner -> new VCPanningModule();
            default -> null;
        };

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
