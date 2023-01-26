/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.knobs;

import com.kadware.koala.PixelDimensions;
import javafx.scene.paint.Color;

/**
 * A knob control with absolute positioning and a minimum and maximum position.
 * The Potentiometer sends the PotentiometerKnobMessage, with a value corresponding to the knob's absolute position.
 * Position ranges from 0.0 to 1.0, inclusive.
 */
public class Potentiometer extends Knob {

    public Potentiometer(
        final int identifier,
        final PixelDimensions dimensions,
        final Color color
    ) {
        super(identifier, dimensions, color);
    }
}
