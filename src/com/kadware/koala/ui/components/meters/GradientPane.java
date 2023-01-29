/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Arrays;

public abstract class GradientPane extends RangedPane {

    protected static final Font GRADIENT_FONT = new Font(8);

    private final String _labelFormat;
    private final double[] _labelPoints;
    private final double[] _tickPoints;

    protected GradientPane(
        final DoubleRange range,
        final Color color,
        final double[] tickPoints,
        final double[] labelPoints,
        final String labelFormat
    ) {
        super(range, color);

        _labelFormat = labelFormat;
        _labelPoints = Arrays.copyOf(labelPoints, labelPoints.length);
        _tickPoints = Arrays.copyOf(tickPoints, tickPoints.length);
    }

    public static GradientPane createGradientPane(
        final DoubleRange range,
        final OrientationType orientation,
        final Color color,
        final double[] tickPoints,
        final double[] labelPoints,
        final String labelFormat
    ) {
        return switch (orientation) {
            case HORIZONTAL -> new HorizontalGradientPane(range, color, tickPoints, labelPoints, labelFormat);
            case VERTICAL -> new VerticalGradientPane(range, color, tickPoints, labelPoints, labelFormat);
        };
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        drawGradient();
    }

    public String getLabelFormat() { return _labelFormat; }
    public double[] getLabelPoints() { return _labelPoints; }
    public double[] getTickPoints() { return _tickPoints; }

    public abstract void drawGradient();
}
