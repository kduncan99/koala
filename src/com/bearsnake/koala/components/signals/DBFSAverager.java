/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.signals;

import com.bearsnake.koala.Koala;
import com.bearsnake.koala.components.ui.Component;
import java.util.Arrays;

/**
 * Collects bipolar-scaled input values and averages them over time to produce a dbFS value.
 * Such values will range from -30.0 to 0.0.
 * (this range may be exceeded if the input values exceed the bipolar range).
 * Used for creating VU meters.
 */
public class DBFSAverager extends Component {

    private static final int DEFAULT_BUFFER_SIZE_MILLIS = 300;
    private static final int DEFAULT_BUFFER_SIZE_SAMPLES = Koala.millisecondsToSamples(DEFAULT_BUFFER_SIZE_MILLIS);
    private static final int PEAK_HOLD_TIME_MILLIS = 1000;
    private static final int PEAK_HOLD_COUNTDOWN_VALUE = (int)(Koala.SAMPLE_RATE * PEAK_HOLD_TIME_MILLIS / 1000.0);

    private boolean _clipOutput;
    private double _dbfsOutput;         //  ranges generally from -30.0 to 0.0
    private double _peakDBFSOutput;     //  as above

    private double _runningTotal;
    private double[] _sampleBuffer;
    private double _previousSample;
    private int _nextSampleIndex;
    private double _peakAverage;
    private int _peakResetCounter;

    public DBFSAverager() {
        _sampleBuffer = new double[DEFAULT_BUFFER_SIZE_SAMPLES];
        _runningTotal = 0;
        _nextSampleIndex = 0;
        _peakAverage = 0;
        _previousSample = 0;
        _peakResetCounter = PEAK_HOLD_COUNTDOWN_VALUE;
        _clipOutput = false;
        _dbfsOutput = -30.0;
        _peakDBFSOutput = -30.0;
    }

    /**
     * Resets the averager
     */
    public void reset() {
        synchronized (this) {
            _sampleBuffer = new double[_sampleBuffer.length];
            _runningTotal = 0;
            _nextSampleIndex = 0;
            _peakAverage = 0;
            _previousSample = 0;
            _peakResetCounter = PEAK_HOLD_COUNTDOWN_VALUE;
            _clipOutput = false;
            _dbfsOutput = -30.0;
            _peakDBFSOutput = -30.0;
        }
    }

    /**
     * Injects a new sample into the averager, which triggers a recalculation of the current output value.
     * @param sample varying from -1.0 to 1.0
     */
    public void inject(
        final double sample
    ) {
        var magnitude = Math.abs(sample);
        synchronized (this) {
            var expiredValue = _sampleBuffer[_nextSampleIndex];
            _sampleBuffer[_nextSampleIndex] = magnitude;

            //  check for clip
            //  defined as any sample exceeding 1.0, or two consecutive sampleCount at 1.0.
            if ((magnitude > 1.0) || (magnitude == 1.0 || _previousSample == 1.0)) {
                _clipOutput = true;
            }

            //  (re)calculate running total
            _runningTotal -= expiredValue;
            _runningTotal += magnitude;
            var average = _runningTotal / _sampleBuffer.length;
            _dbfsOutput = Koala.waveAmplitudeToDBFS(average);

            if (average >= _peakAverage) {
                _peakAverage = average;
                _peakDBFSOutput = _dbfsOutput;
                _peakResetCounter = PEAK_HOLD_COUNTDOWN_VALUE;
            } else {
                _peakResetCounter--;
                if (_peakResetCounter == 0) {
                    _peakAverage = 0.0;
                    _peakDBFSOutput = -30.0;
                }
            }

            _previousSample = magnitude;
            _nextSampleIndex++;
            if (_nextSampleIndex == _sampleBuffer.length)
                _nextSampleIndex = 0;
        }
    }

    public void clearClipIndicator() {
        _clipOutput = false;
    }

    public boolean getClipIndicator() {
        return _clipOutput;
    }

    public double getDBFSValue() {
        return _dbfsOutput;
    }

    public double getPeakValue() {
        return _peakDBFSOutput;
    }

    public void setAveragingSampleCount(
        final int sampleCount
    ) {
        synchronized (this) {
            _sampleBuffer = Arrays.copyOf(_sampleBuffer, sampleCount);
            _previousSample = 0.0;
            if (_nextSampleIndex >= sampleCount)
                _nextSampleIndex = sampleCount - 1;
            _runningTotal = 0;
            Arrays.stream(_sampleBuffer).forEach(s -> _runningTotal += s);
        }
    }
}
