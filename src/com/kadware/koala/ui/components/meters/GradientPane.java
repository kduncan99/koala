/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Range;
import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GradientPane extends Pane {

    private static final BackgroundFill GRADIENT_BG_FILL = new BackgroundFill(Panel.PANEL_CELL_BACKGROUND_COLOR,
                                                                              CornerRadii.EMPTY,
                                                                              Insets.EMPTY);
    private static final Background GRADIENT_BACKGROUND = new Background(GRADIENT_BG_FILL);

    private final Color _color;
    private final String _formatString;
    private final double[] _labelPositions;
    private final Range _range;
    private final double[] _tickPositions;

    public GradientPane(
        final Color color,
        final Range range,
        final double[] tickPositions,
        final double[] labelPositions,
        final String formatString
    ) {
        setBackground(GRADIENT_BACKGROUND);

        _color = color;
        _range = range;
        _tickPositions = tickPositions;
        _labelPositions = labelPositions;
        _formatString = formatString;
    }

    public Color getColor() { return _color; }
    public String getFormatString() { return _formatString; }
    public double[] getLabelPositions() { return _labelPositions; }
    public Range getRange() { return _range; }
    public double[] getTickPositions() { return _tickPositions; }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        setClip(new Rectangle(width, height));
    }

    private void drawCanvas() {

    }
}
