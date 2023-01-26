/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.knobs;

import com.kadware.koala.PixelDimensions;
import javafx.scene.paint.Color;

/**
 * An encoder is a knob with no movement limits - it is continuously rotatable
 * (by using scroll up/down), and it can also be pressed.
 * The Encoder sends the EncoderKnobMessage, with one of the following values:
 *      ENCODER_UP
 *      ENCODER_DOWN
 *      ENCODER_CLICKED
 *      ENCODER_PRESSED
 *      ENCODER_RELEASED
 */
public class Encoder extends Knob {

    public Encoder(
        final int identifier,
        final PixelDimensions dimensions,
        final Color color
    ) {
        super(identifier, dimensions, color);
    }
}
