/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;

/*
 * -----------------------
 * | |       graph       |  Where
 * |a|-------------------|      a:  legend, and does not exist if no legend is given
 * | | gradient markings |      gradient markings also does not exist if GradientInfo is not provided
 * -----------------------
 */
class HorizontalLayoutPane extends LayoutPane {

    public HorizontalLayoutPane(
        final PixelDimensions pixelDimensions,
        final Range<Double> range,
        final String legend,
        final GradientInfo info
    ) {
        super(pixelDimensions, range, legend, info);

        if (legend != null && !legend.isEmpty()) {
            //  legend label needs wrapped in a Group so resizing works properly after it's rotated.
            var legendPane = new Label(legend);
            legendPane.setRotate(-90.0);
            legendPane.setAlignment(Pos.BOTTOM_CENTER);
            legendPane.setTextFill(Panel.PANEL_LEGEND_COLOR);
            var legendGroup = new Group(legendPane);
            add(legendGroup, 0, 0, 1, 2);
        }
    }

    @Override
    public GradientPane createGradientPane(
        final PixelDimensions layoutPixelDimensions,
        final Range<Double> range,
        final boolean hasLegend,
        final GradientInfo info
    ) {
        var gradientPane = (GradientPane) null;
        if (info != null) {
            var w = layoutPixelDimensions.getWidth() - (hasLegend ? 15 : 0);
            var h = layoutPixelDimensions.getHeight() / 2;
            gradientPane = new HorizontalGradientPane(new PixelDimensions(w, h), range, info);
            add(gradientPane, 1, 1, 1, 1);
        }
        return gradientPane;
    }

    @Override
    public GraphPane createGraphPane(
        final PixelDimensions layoutPixelDimensions,
        final Range<Double> range,
        final boolean hasLegend
    ) {
        var subWidth = layoutPixelDimensions.getWidth() - (hasLegend ? 15 : 0);

        var w = layoutPixelDimensions.getWidth() - (hasLegend ? 15 : 0);
        var h = layoutPixelDimensions.getHeight() / 2;
        var graphPane = new GraphPane(new PixelDimensions(w, h), range);
        add(graphPane, 1, 0, 1, 1);
        return graphPane;
    }

    @Override
    public Group createLegendPane(
        final PixelDimensions layoutPixelDimensions,
        final String legend
    ) {
        var grp = (Group) null;

        if (legend != null && !legend.isEmpty()) {
            //  legend label needs wrapped in a Group so resizing works properly after it's rotated.
            var legendPane = new Label(legend);
            legendPane.setRotate(-90.0);
            legendPane.setAlignment(Pos.BOTTOM_CENTER);
            legendPane.setTextFill(Panel.PANEL_LEGEND_COLOR);
            grp = new Group(legendPane);
            add(grp, 0, 0, 1, 2);
        }

        return grp;
    }
}
