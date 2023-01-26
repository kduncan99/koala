/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

/**
 * A graphic indication of continuous values, ranging between the given ranges.
 */
public class LineMeter extends Meter {

    public LineMeter(
        final MeterParams params
    ) {
        super(params,
              new GradientPane(params.getGradientParams()),
              new LineGraphPane(new GraphParams(params.getDimensions(),
                                                params.getRange(),
                                                params.getOrientation(),
                                                params.getScalar(),
                                                params.getColor())));
    }
}
