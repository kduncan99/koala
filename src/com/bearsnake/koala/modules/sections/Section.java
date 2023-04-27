/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.sections;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.modules.Module;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Base class for module sections
 */
public abstract class Section extends GridPane {

    public static final Color BACKGROUND_COLOR = Module.BACKGROUND_COLOR;

    public static final BackgroundFill BACKGROUND_FILL = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
    private static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    protected final CellDimensions _cellDimensions;

    protected Section(
        final CellDimensions cellDimensions
    ) {
        setBorder(Koala.INSET_BORDER);
        setBackground(BACKGROUND);
        _cellDimensions = cellDimensions;
        //  It is up to the derived class to actually create the cells...
    }
}
