/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.Koala;
import com.kadware.koala.Range;
import com.kadware.koala.ui.panels.elements.controlEntities.indicators.IndicatorPane;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 * Subclasses implement the rendering code according to their specificities.
 */
public abstract class Meter extends IndicatorPane {

    private final LayoutPane _layoutPane;
    private final Range<Double> _range;
    private double _value = 0.0;

    public Meter(
        final CellDimensions cellDimensions,
        final String legend,
        final Orientation orientation,
        final Range<Double> range,
        final GradientInfo info
    ) {
        super(cellDimensions);
        _range = range;
        _value = range.getLowValue();

        _layoutPane = switch (orientation) {
            case HORIZONTAL -> new HorizontalLayoutPane(cellDimensions, range, legend, info);
            case VERTICAL -> new VerticalLayoutPane(cellDimensions, range, legend, info);
        };

        add(_layoutPane, 0, 0, 1, 1);
    }

    protected void setValue(
        final double value
    ) {
        _value = Koala.getBounded(_range.getLowValue(), value, _range.getHighValue());
    }

    protected LayoutPane getLayoutPane() { return _layoutPane; }

    public abstract void repaint();
}
