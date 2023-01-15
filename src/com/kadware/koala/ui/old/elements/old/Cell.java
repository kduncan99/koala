/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.elements.old;

import java.awt.*;

//  we extend Container because at least one subclass has multiple components
public class Cell extends Container {

    public static final int CELL_WIDTH_PIXELS = 40;
    public static final int CELL_HEIGHT_PIXELS = 40;
    public static final Dimension CELL_DIMENSION = new Dimension(CELL_WIDTH_PIXELS, CELL_HEIGHT_PIXELS);

    public Cell() {
        setLayout(null);
        setSize(CELL_DIMENSION);
        setPreferredSize(CELL_DIMENSION);
        setMinimumSize(CELL_DIMENSION);
        setMaximumSize(CELL_DIMENSION);
    }
}
