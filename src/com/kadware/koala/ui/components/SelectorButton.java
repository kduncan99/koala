/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components;

import com.kadware.koala.PixelDimensions;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.stream.IntStream;

public class SelectorButton extends Button {

    private final Pane _imagePane;
    private final ImageView[] _imageViews;
    private int _currentSelection;

    public SelectorButton(
        final PixelDimensions dimensions,
        final Image[] images
    ) {
        super(dimensions, Color.GRAY);
        if (images.length < 1)
            throw new RuntimeException("No images for SelectorButton");

        _imagePane = new Pane();
        _imagePane.setPrefSize(dimensions.getWidth(), dimensions.getHeight());
//        getChildren().add(_imagePane);

        _imageViews = new ImageView[images.length];
        IntStream.range(0, images.length).forEach(i -> _imageViews[i] = new ImageView(images[i]));

        _currentSelection = 0;
        _imagePane.getChildren().add(_imageViews[0]);
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
}
