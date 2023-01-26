/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Pair;
import javafx.scene.paint.Color;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class MultiColorBarMeter extends Meter {

    public MultiColorBarMeter(
        final MeterParams params,
        final Pair<Double, Color>[] splitPoints
    ) {
        super(params,
              new GradientPane(params.getGradientParams()),
              new MultiColorGraphPane(new MultiColorGraphParams(params.getDimensions(),
                                                            params.getRange(),
                                                            params.getOrientation(),
                                                            params.getScalar(),
                                                            params.getColor(),
                                                            splitPoints)));
    }
}
