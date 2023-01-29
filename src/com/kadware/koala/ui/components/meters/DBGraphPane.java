/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Koala;
import javafx.scene.paint.Color;

/**
 * More complex than most graph panes.
 * This displays values scaled for dbFS.
 * The gradient is marked out at useful db levels, and is scaled in the same manner as the graph.
 */
public class DBGraphPane extends MultiColorGraphPane {

    final static double[] SPLIT_POINTS = { -3.0, -0.5 };
    final static Color[] COLORS = { Color.GREEN, Color.YELLOW, Color.RED };

    public DBGraphPane(
        final OrientationType orientation,
        final Color color
    ) {
        super(Koala.DBFS_RANGE, orientation, color, SPLIT_POINTS, COLORS);
    }
}
