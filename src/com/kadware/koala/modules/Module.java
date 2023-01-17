/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.exceptions.BadPortIndexException;
import com.kadware.koala.messages.Sender;
import com.kadware.koala.ports.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Module extends Sender {

    public enum ModuleType {
        AREnvelopeGenerator,    //  Simple envelope generator
        Clock,                  //  Logic clock pulse generator
        DADSREnvelopeGenerator, //  Full-featured envelope generator
        DiscreteGlide,          //  Portamento for discrete signals
        DiscreteSequencer,      //  Step sequencer with discrete output values
        DualNoise,              //  Dual (STEREO) noise source
        FixedAmplifier,         //  Basic amplifier (actually an attenuator)
        FixedPanner,            //  Basic stereo pan control
        FixedMixer,             //  Simple 4x2x1 mixer
        Inverter,               //  Inverts the input
        MonoOutput,             //  Routes input to system sound
        Noise,                  //  White noise generator
        Patch,                  //  A patch module
        SimpleEcho,             //  Single tap echo, no VC
        SimpleLFO,              //  Basic low-frequency oscillator
        StereoOutput,           //  Routes L/R channels to system sound
        TestTone,               //  Fixed (but settable) frequency square-wave oscillator
        VCAmplifier,            //  Modulate-able amplifier
        VCFilter,               //  Simple VCFilter
        VCMixer,                //  Mixer where level and pan are controlled by modulation inputs
        VCOscillator,           //  General-purpose single-mode oscillator
        VCPanner,               //  Stereo panner with modulation control
    }

    final Map<Integer, IInputPort> _inputPorts = new HashMap<>();
    final Map<Integer, IOutputPort> _outputPorts = new HashMap<>();

    public abstract void advance();
    public abstract void close();

    public final void detachAllPorts() {
        for (IInputPort port : _inputPorts.values()) {
            port.disconnect();
        }
    }

    public final IInputPort getInputPort(
        final int portIndex
    ) {
        if (_inputPorts.containsKey(portIndex)) {
            return _inputPorts.get(portIndex);
        } else {
            throw new BadPortIndexException(portIndex);
        }
    }

    public abstract ModuleType getModuleType();

    public final IOutputPort getOutputPort(
        final int portIndex
    ) {
        if (_outputPorts.containsKey(portIndex)) {
            return _outputPorts.get(portIndex);
        } else {
            throw new BadPortIndexException(portIndex);
        }
    }

    public void reset() {
        for (IInputPort port : _inputPorts.values()) {
            port.reset();
        }
        for (IOutputPort port : _outputPorts.values()) {
            port.reset();
        }
    }
}
