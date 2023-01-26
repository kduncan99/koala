/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;

public class GraphParams {

    private final Color _color;
    private final PixelDimensions _dimensions;
    private final OrientationType _orientationType;
    private final DoubleRange _range;
    private final Scalar _scalar;

    public GraphParams(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final OrientationType orientation,
        final Scalar scalar,
        final Color color
    ) {
        _color = color;
        _dimensions = dimensions;
        _range = range;
        _orientationType = orientation;
        _scalar = scalar;
    }

    public Color getColor() { return _color; }
    public PixelDimensions getDimensions() { return _dimensions; }
    public OrientationType getOrientationType() { return _orientationType; }
    public DoubleRange getRange() { return _range; }
    public Scalar getScalar() { return _scalar; }
}
