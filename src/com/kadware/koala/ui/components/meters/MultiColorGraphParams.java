/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Pair;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;

public class MultiColorGraphParams extends GraphParams {

    private final Pair<Double, Color>[] _splitPoints;

    public MultiColorGraphParams(
        final PixelDimensions dimensions,
        final DoubleRange range,
        final OrientationType orientation,
        final Scalar scalar,
        final Color color,
        final Pair<Double, Color>[] splitPoints
    ) {
        super(dimensions, range, orientation, scalar, color);
        _splitPoints = splitPoints;
    }

    public Pair<Double, Color>[] getSplitPoints() { return _splitPoints; }
}
