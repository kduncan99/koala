/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.meters;

import com.bearsnake.koala.Koala;
import javafx.scene.paint.Color;

/**
 * More complex than most graph panes.
 * This displays values scaled for dbFS.
 */
public class DBGraphPane extends MultiColorGraphPane {

    final static double[] SPLIT_POINTS = { -3.0, -1.0 };
    final static Color[] COLORS = { Color.GREEN, Color.YELLOW, Color.RED };

    public DBGraphPane(
        final OrientationType orientation,
        final Color color
    ) {
        super(Koala.DBFS_RANGE, orientation, color, SPLIT_POINTS, COLORS);
    }
}
