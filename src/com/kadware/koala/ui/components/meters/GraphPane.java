/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class GraphPane extends Pane {

    private final GraphParams _params;

    public GraphPane(
        final GraphParams params
    ) {
        _params = params;

        var bgColor = _params.getColor().darker().darker();
        setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        setClip(new Rectangle(width, height));
    }

    public GraphParams getParams() { return _params; }

    public abstract void setValue(double value);
}
