/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GradientPane extends Pane {

    private static final BackgroundFill GRADIENT_BG_FILL =
        new BackgroundFill(Panel.PANEL_CELL_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
    private static final Background GRADIENT_BACKGROUND = new Background(GRADIENT_BG_FILL);

    private final GradientParams _params;

    public GradientPane(
        final GradientParams params
    ) {
        _params = params;
        setBackground(GRADIENT_BACKGROUND);
    }

    public Color getColor() { return _params.getColor(); }
    public String getFormatString() { return _params.getFormatString(); }
    public double[] getLabelPositions() { return _params.getLabelPositions(); }
    public DoubleRange getRange() { return _params.getRange(); }
    public double[] getTickPositions() { return _params.getTickPositions(); }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        setClip(new Rectangle(width, height));
    }
}
