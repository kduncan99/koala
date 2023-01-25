/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components;

import com.kadware.koala.PixelDimensions;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public abstract class Button extends StackPane {

    private final StackPane _outlinePane;
    private final Canvas _outlinePressed;
    private final Canvas _outlineReleased;

    public Button(
        final PixelDimensions dimensions,
        final Color color
    ) {
        setMinSize(dimensions.getWidth(), dimensions.getHeight());
        setPrefSize(dimensions.getWidth(), dimensions.getHeight());
        setMaxSize(dimensions.getWidth(), dimensions.getHeight());
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

        _outlinePane = new StackPane();
        _outlinePane.setPrefSize(dimensions.getWidth(), dimensions.getHeight());
        getChildren().add(_outlinePane);

        var inset = 2;
        var x1 = inset;
        var y1 = inset;
        var x2 = dimensions.getWidth() - inset - 1;
        var y2 = dimensions.getHeight() - inset - 1;

        _outlinePressed = new Canvas(dimensions.getWidth(), dimensions.getHeight());
        var gc1 = _outlinePressed.getGraphicsContext2D();
        gc1.setStroke(Color.BLACK);
        gc1.strokeLine(x1, y2, x1, y1);
        gc1.strokeLine(x1, y1, x2, y1);
        gc1.setStroke(Color.LIGHTGRAY);
        gc1.strokeLine(x2, y1, x2, y2);
        gc1.strokeLine(x2, y2, x1, y2);
        _outlinePane.getChildren().add(_outlinePressed);

        _outlineReleased = new Canvas(dimensions.getWidth(), dimensions.getHeight());
        var gc2 = _outlineReleased.getGraphicsContext2D();
        gc2.setStroke(Color.LIGHTGRAY);
        gc2.strokeLine(x1, y2, x1, y1);
        gc2.strokeLine(x1, y1, x2, y1);
        gc2.setStroke(Color.BLACK);
        gc2.strokeLine(x2, y1, x2, y2);
        gc2.strokeLine(x2, y2, x1, y2);
        _outlinePane.getChildren().add(_outlineReleased);

        setPressed(false);

        setOnMouseClicked(this::mouseClicked);
        setOnMousePressed(this::mousePressed);
        setOnMouseReleased(this::mouseReleased);
    }

    protected StackPane getOutlinePane() { return _outlinePane; }

    protected abstract void mouseClicked(final MouseEvent event);

    protected void mousePressed(
        final MouseEvent event
    ) {
        _outlinePressed.toFront();
        setPressed(true);
    }

    protected void mouseReleased(
        final MouseEvent event
    ) {
        _outlineReleased.toFront();
        setPressed(false);
    }
}
