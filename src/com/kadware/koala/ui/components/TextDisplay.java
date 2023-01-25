/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components;

import com.kadware.koala.PixelDimensions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * An indicator which relays some sort of information, such as an effective control setting
 * which might be the summation of several control factors, or perhaps simply a textual
 * or numeric indication of a value which isn't appropriate to be displayed within a control.
 */
public class TextDisplay extends Label {

    private static final Font FONT = new Font("Courier New", 12);

    public TextDisplay(
        final PixelDimensions dimensions,
        final Color color
    ) {
        var fgColor = color.brighter();
        setPrefSize(dimensions.getWidth(), dimensions.getHeight());
        setTextFill(fgColor);
        var bgColor = color.darker().darker();
        setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        var bs = new BorderStroke(Color.BLACK,
                                  BorderStrokeStyle.SOLID,
                                  CornerRadii.EMPTY,
                                  BorderWidths.DEFAULT,
                                  Insets.EMPTY);
        setBorder(new Border(bs));
        setFont(FONT);
        setAlignment(Pos.CENTER);
    }

    //  Only to be invoked on the Application thread
    public void setValue(
        final String value
    ) {
        setText(value);
    }
}
