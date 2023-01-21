/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import javafx.scene.paint.Color;

public class GradientInfo {

    public final Color _color;
    public final double[] _labelPoints;
    public final int _numberOfTickMarks;

    public GradientInfo(
        final Color color,
        final int numberOfTickMarks,
        final double[] labelPoints
    ) {
        _color = color;
        _labelPoints = labelPoints;
        _numberOfTickMarks = numberOfTickMarks;
    }
}
