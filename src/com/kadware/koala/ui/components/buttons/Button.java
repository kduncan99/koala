/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.buttons;

import com.kadware.koala.PixelDimensions;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public abstract class Button extends Pane {

    private final Line _topLine;
    private final Line _leftLine;
    private final Line _rightLine;
    private final Line _bottomLine;

    public Button(
        final PixelDimensions dimensions
    ) {
        var w = dimensions.getWidth();
        var h = dimensions.getHeight();
        setPrefSize(w, h);

        _topLine = new Line(0, 0, w - 1, 0);
        _leftLine = new Line(0, 0, 0, h - 1);
        _rightLine = new Line(w - 1, 0, w - 1, h - 1);
        _bottomLine = new Line(0, h - 1, w - 1, h - 1);

        getChildren().addAll(_topLine, _leftLine, _rightLine, _bottomLine);

        setPressed(false);
        _leftLine.setStroke(Color.LIGHTGRAY);
        _topLine.setStroke(Color.LIGHTGRAY);
        _rightLine.setStroke(Color.BLACK);
        _bottomLine.setStroke(Color.BLACK);

        setOnMouseClicked(this::mouseClicked);
        setOnMousePressed(this::mousePressed);
        setOnMouseReleased(this::mouseReleased);
    }

    protected abstract void mouseClicked(final MouseEvent event);

    protected void mousePressed(
        final MouseEvent event
    ) {
        setPressed(true);
        _leftLine.setStroke(Color.BLACK);
        _topLine.setStroke(Color.BLACK);
        _rightLine.setStroke(Color.LIGHTGRAY);
        _bottomLine.setStroke(Color.LIGHTGRAY);
    }

    protected void mouseReleased(
        final MouseEvent event
    ) {
        setPressed(false);
        _leftLine.setStroke(Color.LIGHTGRAY);
        _topLine.setStroke(Color.LIGHTGRAY);
        _rightLine.setStroke(Color.BLACK);
        _bottomLine.setStroke(Color.BLACK);
    }
}
