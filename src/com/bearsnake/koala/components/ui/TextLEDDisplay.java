/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui;

import com.bearsnake.koala.PixelDimensions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * An indicator which relays some sort of information, such as an effective control setting
 * which might be the summation of several control factors, or perhaps simply a textual
 * or numeric indication of a value which isn't appropriate to be displayed within a control.
 */
public class TextLEDDisplay extends InactiveComponent {

    private static final Font FONT = new Font("Courier New", 14);

    private final Label _label;

    public TextLEDDisplay(
        final PixelDimensions dimensions,
        final Color color
    ) {
        _label = new Label();
        var fgColor = color.brighter();
        _label.setPrefSize(dimensions.getWidth(), dimensions.getHeight());
        _label.setTextFill(fgColor);
        var bgColor = color.darker().darker().darker();
        _label.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        var bs = new BorderStroke(Color.BLACK,
                                  BorderStrokeStyle.SOLID,
                                  CornerRadii.EMPTY,
                                  BorderWidths.DEFAULT,
                                  Insets.EMPTY);
        _label.setBorder(new Border(bs));
        _label.setFont(FONT);
        _label.setAlignment(Pos.CENTER);

        getChildren().add(_label);
    }

    //  Only to be invoked on the Application thread
    public void setValue(final String value) { _label.setText(value); }
}
