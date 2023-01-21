/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.Koala;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.layout.Pane;

public class GraphPane extends Pane {

    protected static final int INSET_PIXELS = 2;

    private final PixelDimensions _pixelDimensions;
    private final Range<Double> _range;

    protected GraphPane(
        final PixelDimensions dimensions,
        final Range<Double> range
    ) {
        _pixelDimensions = dimensions;
        _range = range;
        setPrefSize(dimensions.getWidth(), dimensions.getHeight());
    }

    public PixelDimensions getPixelDimensions() { return _pixelDimensions; }
    public Range<Double> getRange() { return _range; }

    /**
     * Given a raw value, relative to the established low/high range,
     * we produce a coordinate relative to the left edge of the graph which
     * represents that value (clipping to min/max if/as necessary)
     */
    public double findHorizontalGraphCoordinate(
        final double rawValue
    ) {
        var ratio = (rawValue - _range.getLowValue()) / (_range.getHighValue() - _range.getLowValue());
        ratio = Koala.getBounded(0.0, ratio, 1.0);
        return (ratio * getPrefWidth());
    }

    /**
     * As above, but in the vertical dimension
     */
    public double findVerticalGraphCoordinate(
        final double rawValue
    ) {
        var ratio = (rawValue - _range.getLowValue()) / (_range.getHighValue() - _range.getLowValue());
        ratio = Koala.getBounded(0.0, ratio, 1.0);
        return (ratio * getPrefHeight());
    }
}
