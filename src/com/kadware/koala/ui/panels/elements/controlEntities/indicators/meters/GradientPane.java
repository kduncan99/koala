/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

/**
 * We have a background, a tick-marks canvas, and a variety of labels from bottom to top in z-order
 */
public abstract class GradientPane extends StackPane {

    protected static final int INSET_PIXELS = 2;

    protected final Range<Double> _range;
    protected final Canvas _tickMarkCanvas;

    protected GradientPane(
        final PixelDimensions dimensions,
        final Range<Double> range,
        final GradientInfo info
    ) {
        _tickMarkCanvas = new Canvas(dimensions.getWidth(), dimensions.getHeight());
        _range = range;

        setPrefSize(dimensions.getWidth(), dimensions.getHeight());
        drawCanvas(_tickMarkCanvas, range, info);
        getChildren().add(_tickMarkCanvas);
    }

    protected abstract void drawCanvas(final Canvas canvas,
                                       final Range<Double> meterRange,
                                       final GradientInfo info);
}
