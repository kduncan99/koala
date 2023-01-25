/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala;

public class PixelDimensions extends Dimensions<Integer> {

    public PixelDimensions(
        final int width,
        final int height
    ) {
        super(width, height);
    }

    public PixelDimensions expandBy(
        final int pixels
    ) {
        return new PixelDimensions(getWidth() + pixels, getHeight() + pixels);
    }
}
