/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components;

import com.kadware.koala.PixelDimensions;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ImageButton extends Button {

    private Canvas _canvas;

    public ImageButton(
        final PixelDimensions dimensions,
        final Color color,
        final Image image
    ) {
        super(dimensions, color);
        _canvas = new Canvas(dimensions.getWidth(), dimensions.getHeight());
        var gc = _canvas.getGraphicsContext2D();
//        gc.drawImage(image);
        gc.fillText("X", 4, dimensions.getHeight() - 4);
        getOutlinePane().getChildren().add(_canvas);
    }

    protected void mouseClicked(
        final MouseEvent event
    ) {
//        _imagePane.getChildren().removeAll();
//        _currentSelection++;
//        if (_currentSelection == _imageViews.length)
//            _currentSelection = 0;
//        _imagePane.getChildren().add(_imageViews[_currentSelection]);
    }

    @Override
    protected void mousePressed(
        final MouseEvent event
    ) {
        super.mousePressed(event);
        _canvas.setLayoutX(_canvas.getLayoutX() + 1);
        _canvas.setLayoutY(_canvas.getLayoutY() + 1);
        _canvas.toFront();
    }

    @Override
    protected void mouseReleased(
        final MouseEvent event
    ) {
        super.mouseReleased(event);
        _canvas.setLayoutX(_canvas.getLayoutX() - 1);
        _canvas.setLayoutY(_canvas.getLayoutY() - 1);
        _canvas.toFront();
    }
}
