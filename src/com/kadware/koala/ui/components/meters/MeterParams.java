/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class MeterParams {

    private final Color _color;
    private final PixelDimensions _dimensions;
    private final GradientParams _gradientParams;
    private final OrientationType _orientationType;
    private final DoubleRange _range;
    private final Scalar _scalar;

    public MeterParams(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final OrientationType orientationType,
        final Scalar scalar,
        final Color color,
        final GradientParams gradientParams
    ) {
        _color = color;
        _dimensions = dimensions;
        _gradientParams = gradientParams;
        _orientationType = orientationType;
        _range = range;
        _scalar = scalar;
    }

    public Color getColor() { return _color; }
    public PixelDimensions getDimensions() { return _dimensions; }
    public GradientParams getGradientParams() { return _gradientParams; }
    public OrientationType getOrientation() { return _orientationType; }
    public DoubleRange getRange() { return _range; }
    public Scalar getScalar() { return _scalar; }
}
