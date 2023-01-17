package com.kadware.koala.ui.panels.elements.indicators;

import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 * Subclasses implement the rendering code according to their specificities.
 */
public class Meter {

    public enum DisplayMode {
        BAR,
        POINT,
    }

    public enum Scale {
        LINEAR,
        VU,
    }

    private final Color _color;
    private final DisplayMode _displayMode;
    private double _lowLimit;
    private double _highLimit;
    private double _sample;

    public Meter(
        final Color color,
        final DisplayMode displayMode,
        final double lowLimit,
        final double highLimit
    ) {
        _color = color;
        _displayMode = displayMode;
        _lowLimit = lowLimit;
        _highLimit = highLimit;
        _sample = lowLimit;
    }

    //TODO so we do runLater() inside here, and give it a function which does all the real stuffs? (i.e., protected paint())
    public void setSample(
        final double value
    ) {
        _sample = Math.max(Math.min(value, _highLimit), _lowLimit);
    }
}
