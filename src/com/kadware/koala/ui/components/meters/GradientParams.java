/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GradientParams extends Pane {

    private final Color _color;
    private final String _formatString;
    private final double[] _labelPositions;
    private final DoubleRange _range;
    private final double[] _tickPositions;

    public GradientParams(
        final Color color,
        final DoubleRange range,
        final double[] tickPositions,
        final double[] labelPositions,
        final String formatString
    ) {
        _color = color;
        _range = range;
        _tickPositions = tickPositions;
        _labelPositions = labelPositions;
        _formatString = formatString;
    }

    public Color getColor() { return _color; }
    public String getFormatString() { return _formatString; }
    public double[] getLabelPositions() { return _labelPositions; }
    public DoubleRange getRange() { return _range; }
    public double[] getTickPositions() { return _tickPositions; }
}
