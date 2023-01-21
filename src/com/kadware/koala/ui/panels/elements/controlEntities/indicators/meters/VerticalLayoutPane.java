/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

/*
 * -----
 * | | |    Where
 * |a|b|        a:  gradient markings; does not exist if GradientInfo is not provided
 * | | |        b:  graph
 * |---|        c:  legend, and does not exist if no legend is given
 * | c |
 * -----
 */
class VerticalLayoutPane extends LayoutPane {

    public VerticalLayoutPane(
        final CellDimensions cellDimensions,
        final Range<Double> range,
        final String legend,
        final GradientInfo info
    ) {
        super(cellDimensions, range, legend, info);

        //TODO
//        var legendPane = new Label(legend);
//        legendPane.setAlignment(Pos.BOTTOM_CENTER);
//        legendPane.setTextFill(Panel.PANEL_LEGEND_COLOR);
//        legendPane.setPrefWidth(_width);
//        var legendGroup = new Group(legendPane);
//
//        _graphPane = new Pane();
//        _graphPane.setPrefWidth(_width / 2.0);
//        _graphPane.setPrefHeight(_height - 15);
//
//        add(_gradientPane, 0, 0, 1, 1);
//        add(_graphPane, 1, 0, 1, 1);
//        add(legendGroup, 0, 1, 2, 1);
    }

    @Override
    public GradientPane createGradientPane(
        final PixelDimensions layoutPixelDimensions,
        final Range<Double> range,
        final boolean hasLegend,
        final GradientInfo info
    ) {
        return null;//TODO
    }

    @Override
    public Pane createGraphPane(
        final PixelDimensions layoutPixelDimensions,
        final boolean hasLegend
    ) {
        return null;//TODO
    }

    @Override
    public Group createLegendPane(
        final PixelDimensions layoutPixelDimensions,
        final String legend
    ) {
        return null;//TODO
    }
}
