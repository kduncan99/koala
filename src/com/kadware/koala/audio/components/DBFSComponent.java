/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.components;

import com.kadware.koala.Koala;
import java.util.Arrays;

public class DBFSComponent extends Component {

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

    public DBFSComponent() {
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

    @Override
    public void advance() {}

    @Override
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

    public void inject(
        final double sample
    ) {
        synchronized (this) {
            _sampleBuffer[_nextSampleIndex] = sample;

            //  check for clip
            //  defined as any sample exceeding 1.0, or two consecutive samples at 1.0.
            if ((sample > 1.0) || (sample == 1.0 || _previousSample == 1.0)) {
                _clipOutput = true;
            }

            //  (re)calculate running total
            var oldestIndex = _nextSampleIndex - 1;
            if (oldestIndex < 0)
                oldestIndex = _sampleBuffer.length - 1;
            _runningTotal -= _sampleBuffer[oldestIndex];
            _runningTotal += sample;
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

            _previousSample = sample;
            _nextSampleIndex++;
            if (_nextSampleIndex == _sampleBuffer.length)
                _nextSampleIndex = 0;
        }
    }

    public void clearClip() {
        _clipOutput = false;
    }

    public boolean getClipValue() {
        return _clipOutput;
    }

    public double getDBFSValue() {
        return _dbfsOutput;
    }

    public double getPeakValue() {
        return _peakDBFSOutput;
    }

    public void setAveragingSamples(
        final int samples
    ) {
        synchronized (this) {
            _sampleBuffer = Arrays.copyOf(_sampleBuffer, samples);
            _previousSample = 0.0;
            if (_nextSampleIndex >= samples)
                _nextSampleIndex = samples - 1;
            _runningTotal = 0;
            Arrays.stream(_sampleBuffer).forEach(s -> _runningTotal += s);
        }
    }

    @Override
    public void close() {}
}
