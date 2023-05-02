/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.modules.elements.Element;

/**
 * Base class for active and blank ports
 */
public abstract class Port extends Element {

    protected static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    protected static final PixelDimensions PIXEL_DIMENSIONS = determinePixelDimensions(CELL_DIMENSIONS);
    public static final int HORIZONTAL_PIXELS_PER_CELL = 40;
    public static final int VERTICAL_PIXELS_PER_CELL = 40;

    public static final PixelDimensions GRAPHIC_DIMENSIONS =
        new PixelDimensions(HORIZONTAL_PIXELS_PER_CELL, VERTICAL_PIXELS_PER_CELL / 2);

    protected Port() {
        super(CELL_DIMENSIONS, PIXEL_DIMENSIONS);
        setPrefSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());
    }

    /**
     * Determines the dimensions of a prospective port in pixels given the cell dimensions of that port.
     * Generally, a Port is always 1x1, but we'll do this anyway, just in case.
     * @param cellDimensions cell dimensions of a port
     * @return pixel dimension object
     */
    public static PixelDimensions determinePixelDimensions(
        final CellDimensions cellDimensions
    ) {
        var width = cellDimensions.getWidth() * HORIZONTAL_PIXELS_PER_CELL;
        var height = cellDimensions.getHeight() * VERTICAL_PIXELS_PER_CELL;
        return new PixelDimensions(width, height);
    }
}
