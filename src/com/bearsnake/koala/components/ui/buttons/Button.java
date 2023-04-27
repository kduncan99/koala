/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.buttons;

import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.components.ui.ActiveComponent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public abstract class Button extends ActiveComponent {

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

    protected void setButtonPressed(
        final boolean value
    ) {
        setPressed(value);
        if (value) {
            _leftLine.setStroke(Color.BLACK);
            _topLine.setStroke(Color.BLACK);
            _rightLine.setStroke(Color.LIGHTGRAY);
            _bottomLine.setStroke(Color.LIGHTGRAY);
        } else {
            _leftLine.setStroke(Color.LIGHTGRAY);
            _topLine.setStroke(Color.LIGHTGRAY);
            _rightLine.setStroke(Color.BLACK);
            _bottomLine.setStroke(Color.BLACK);
        }
    }

    protected abstract void mouseClicked(final MouseEvent event);
    protected abstract void mousePressed(final MouseEvent event);
    protected abstract void mouseReleased(final MouseEvent event);
}
