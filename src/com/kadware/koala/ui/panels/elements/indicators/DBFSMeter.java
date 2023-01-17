/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.indicators;

import java.util.Arrays;

/**
 * This is a vertically-oriented dbFS VU meter.
 * The layout is as such:
 * -----
 * | | |
 * |a|b|
 * | | |
 * -----
 * where a is a gradient text display with db gradients at periodic intervals
 *   and b is a canvas upon which we draw the meter.
 *
 * Since Indicator is a Pane, we have to set up an HBox inside ourselves to accomplish this.
 */
public class DBFSMeter extends Indicator {

    private static final int AVERAGING_SAMPLE_COUNT = 1;
    private static final int MAX_AVERAGING_SAMPLE_COUNT = 100000;
    private static final int MIN_AVERAGING_SAMPLE_COUNT = 1;

    public enum DisplayMode {
        BAR,
        DOT
    }

    private double[] _samples;
    private int _nextSampleIndex;
    private boolean _enablePeakHold;
    private boolean _enableClipIndicator;
    private int _peakHoldSampleCount;

    public DBFSMeter(
        final int width,
        final int height
    ) {
        // TODO we used to be a subclass of Pane, but now we're an HBox...
        //  but we're also an Indicator, and an Indicator might not be an HBox, so... what to do?
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        _samples = new double[AVERAGING_SAMPLE_COUNT];
        _nextSampleIndex = 0;
    }

    public void setAveragingSampleCount(
        final int count
    ) {
        var actual = Math.max(count, MIN_AVERAGING_SAMPLE_COUNT);
        actual = Math.min(actual, MAX_AVERAGING_SAMPLE_COUNT);
        _samples = Arrays.copyOf(_samples, actual);
        if (_nextSampleIndex > count)
            _nextSampleIndex = 0;
    }

    //  TODO set various geometries

    /*
     * values are expressed in the range -1 <= v <= 1
     */
    public void setSample(
        final double value
    ) {
        _samples[_nextSampleIndex] = Math.abs(value);
        _nextSampleIndex++;
        if (_nextSampleIndex >= _samples.length)
            _nextSampleIndex = 0;
        //  TODO cannot draw here, it's the wrong thread
    }

    //  TODO need some way to repaint on demand...
    //  TODO green for reference level (from -inf to reference level) max -16db
    //      yellow for standard level (up to 10db beyond reference level) max -6db
    //      red for up to 6db beyond standard level - max 0db
    //      are we doing peak-hold?
    //      are we doing clip-indication?
}
