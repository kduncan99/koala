/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;

public class SplitBarGraphParams extends GraphParams {

    private final double _splitPoint;

    public SplitBarGraphParams(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final OrientationType orientation,
        final Scalar scalar,
        final Color color,
        final double splitPoint
    ) {
        super(dimensions, range, orientation, scalar, color);
        _splitPoint = splitPoint;
    }

    public double getSplitPoint() { return _splitPoint; }
}
