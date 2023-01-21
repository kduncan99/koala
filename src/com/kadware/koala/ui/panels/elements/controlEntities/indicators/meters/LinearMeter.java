/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.Range;
import javafx.scene.paint.Color;

/**
 * This is a vertically-oriented linear meter.
 * The layout is as such:
 * -----
 * | | |
 * |a|b|
 * | | |
 * -----
 * where a is a gradient text display with gradient markers at periodic intervals
 *   and b is a rectangle
 *
 * Since Indicator is a Pane, we have to set up an HBox inside ourselves to accomplish this.
 */
public class LinearMeter extends Meter {

    public enum Mode {
        BAR,
        DOT,
        LINE,
    }

    private final Color _fillColor;
    private final Color _bgColor;
    private final Mode _mode;

    public LinearMeter(
        final CellDimensions cellDimensions,
        final String legend,
        final Color color,
        final Mode mode,
        final Range<Double> range,
        final GradientInfo info
    ) {
        super(cellDimensions, legend, Orientation.HORIZONTAL, range, info);

        _fillColor = color.brighter();
        _bgColor = color.darker().darker();
        _mode = mode;
    }

    @Override
    public void repaint() {
        var gp = getLayoutPane()._graphPane;

        switch (_mode) {
            case BAR: {
                //TODO
            }

            case DOT: {
                //TODO
            }

            case LINE: {
                //TODO
            }
        }
    }
}
