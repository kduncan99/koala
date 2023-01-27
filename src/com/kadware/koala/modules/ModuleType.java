/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

public enum ModuleType {
    AREnvelopeGenerator,    //  Simple envelope generator
    Clock,                  //  Logic clock pulse generator
    DADSREnvelopeGenerator, //  Full-featured envelope generator
    DiscreteGlide,          //  Portamento for discrete signals
    DiscreteSequencer,      //  Step sequencer with discrete output values
    DualNoise,              //  Dual (STEREO) noise source
    FixedAttenuator,        //  Basic amplifier (actually an attenuator)
    FixedPanner,            //  Basic stereo pan control
    FixedMixer,             //  Simple 4x2x1 mixer
    Inverter,               //  Inverts the input
    MonoOutput,             //  Routes input to system sound
    Noise,                  //  White noise generator
    Patch,                  //  A patch module
    SimpleEcho,             //  Single tap echo, no VC
    SimpleLFO,              //  Basic low-frequency oscillator
    SimpleOscillator,       //  No-frills oscillator
    StereoOutput,           //  Routes L/R channels to system sound
    VCAmplifier,            //  Modulate-able amplifier
    VCFilter,               //  Simple VCFilter
    VCMixer,                //  Mixer where level and pan are controlled by modulation inputs
    VCOscillator,           //  General-purpose single-mode oscillator
    VCPanner,               //  Stereo panner with modulation control
}
