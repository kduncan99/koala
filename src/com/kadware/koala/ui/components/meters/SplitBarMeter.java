/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class SplitBarMeter extends Meter {

    public SplitBarMeter(
        final MeterParams params,
        final double splitPoint
    ) {
        super(params,
              new GradientPane(params.getGradientParams()),
              new SplitBarGraphPane(new SplitBarGraphParams(params.getDimensions(),
                                                            params.getRange(),
                                                            params.getOrientation(),
                                                            params.getScalar(),
                                                            params.getColor(),
                                                            splitPoint)));
    }
}
