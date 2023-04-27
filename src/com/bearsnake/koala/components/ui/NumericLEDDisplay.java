/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui;

import com.bearsnake.koala.PixelDimensions;
import javafx.scene.paint.Color;

/**
 * An indicator which relays some sort of information, such as an effective control setting
 * which might be the summation of several control factors, or perhaps simply a textual
 * or numeric indication of a value which isn't appropriate to be displayed within a control.
 */
public class NumericLEDDisplay extends TextLEDDisplay {

    private final String _formatString;

    public NumericLEDDisplay(
        final PixelDimensions dimensions,
        final Color color,
        final String formatString
    ) {
        super(dimensions, color);
        _formatString = formatString;
    }

    //  Only to be invoked on the Application thread
    public void setValue(
        final double value
    ) {
        super.setValue(String.format(_formatString, value).trim());
    }
}
