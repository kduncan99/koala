/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Range;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public abstract class BaseOrientation {

    protected static final Font GRADIENT_FONT = new Font(8);

    public abstract void applyOrientation(
        final GridPane parent,
        final GraphPane graphPane,
        final GradientPane gradientPane
    );

    public abstract void drawGradient(
        final GradientPane gradientPane,
        final Scalar scalar
    );

    public abstract double getGraphCenterPointX(
        final GraphPane graphPane,
        final Range range,
        final Scalar scalar,
        final double value
    );

    public abstract double getGraphCenterPointY(
        final GraphPane graphPane,
        final Range range,
        final Scalar scalar,
        final double value
    );
}