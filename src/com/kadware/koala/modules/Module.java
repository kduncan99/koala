/**
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.exceptions.BadPortException;
import com.kadware.koala.ports.InputPort;
import com.kadware.koala.ports.OutputPort;
import com.kadware.koala.ports.Port;
import java.util.HashMap;
import java.util.Map;

public abstract class Module {

    public static enum ModuleType {
        Amplifier,              //  Basic VCA
        AREnvelopeGenerator,    //  Simple envelope generator
        Inverter,               //  Inverts the input
        MonoOutput,             //  Routes input to system sound
        Noise,                  //  White noise generator
        Oscillator,             //  General-purpose single-mode oscillator
        TestTone,               //  Fixed (but settable) frequency square-wave oscillator
        StereoOutput,           //  Routes L/R channels to system sound
        //  TODO ADSR HADSR VCF Mixer PanningVCA S/H Delay MultiModeOsc MultiOsc (with slop)
        //  TODO Sequencer OctaveDivider PitchShifter RingModulator Compressor/Limiter
        //  TODO MIDI
        //  TODO Voice (owns a set of connected modules) (so we can duplicate it for polyphony)
        //  TODO Linked modules, so setting one sets all the corresponding others in a polyphony set
    }

    final Map<Integer, InputPort> _inputPorts = new HashMap<>();
    final Map<Integer, OutputPort> _outputPorts = new HashMap<>();

    public abstract void advance();
    public abstract void close();

    public final void detachAllPorts() {
        for (InputPort port : _inputPorts.values()) {
            port.disconnect();
        }
    }

    public final InputPort getInputPort(
        final int portIndex
    ) {
        if (_inputPorts.containsKey(portIndex)) {
            return _inputPorts.get(portIndex);
        } else {
            throw new BadPortException(portIndex);
        }
    }

    public abstract String getModuleClass();
    public abstract ModuleType getModuleType();

    public final OutputPort getOutputPort(
        final int portIndex
    ) {
        if (_outputPorts.containsKey(portIndex)) {
            return _outputPorts.get(portIndex);
        } else {
            throw new BadPortException(portIndex);
        }
    }

    public void reset() {
        for (Port port : _inputPorts.values()) {
            port.reset();
        }
        for (Port port : _outputPorts.values()) {
            port.reset();
        }
    }
}
