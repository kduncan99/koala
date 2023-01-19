package com.kadware.koala.ui.panels.elements.controlEntities.indicators;

import com.kadware.koala.Koala;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 * Subclasses implement the rendering code according to their specificities.
 */
public abstract class Meter extends IndicatorPane {

    private final double _lowLimit;
    private final double _highLimit;
    private double _value;

    public Meter(
        final int horizontalCellCount,
        final int verticalCellCount,
        final double lowLimit,
        final double highLimit
    ) {
        super(horizontalCellCount, verticalCellCount);
        _lowLimit = lowLimit;
        _highLimit = highLimit;
        _value = lowLimit;
    }

    //TODO so we do runLater() inside here, and give it a function which does all the real stuffs? (i.e., protected paint())
    //  BUT... not every time, max out at around 100Hz or so, sufficiently greater than the 60Hz refresh, but not too much
    public void setSample(
        final double value
    ) {
        _value = Koala.getBounded(_lowLimit, value, _highLimit);
    }
}
