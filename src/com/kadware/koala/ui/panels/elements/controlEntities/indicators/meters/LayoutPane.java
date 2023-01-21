/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.indicators.meters;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.Range;
import com.kadware.koala.ui.panels.elements.controlEntities.ControlEntityPane;
import javafx.scene.Group;
import javafx.scene.layout.*;

/**
 * A layout for a meter.
 */
abstract class LayoutPane extends GridPane {

    private final GradientPane _gradientPane;
    private final GraphPane _graphPane;
    private final Group _legendPane;
    private final PixelDimensions _pixelDimensions;

    protected LayoutPane(
        final PixelDimensions pixelDimensions,
        final Range<Double> range,
        final String legend,
        final GradientInfo info
    ) {
        _pixelDimensions = pixelDimensions;
        setPrefSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());

        var hasLegend = !(legend == null || legend.isEmpty());
        _legendPane = createLegendPane(_pixelDimensions, legend);
        _graphPane = createGraphPane(_pixelDimensions, range, hasLegend);
        _gradientPane = createGradientPane(_pixelDimensions, range, hasLegend, info);
    }

    public GradientPane getGradientPane() { return _gradientPane; }
    public GraphPane getGraphPane() { return _graphPane; }
    public Group getLegendPane() { return _legendPane; }
    public PixelDimensions getPixelDimensions() { return _pixelDimensions; }

    public abstract GradientPane createGradientPane(
        final PixelDimensions layoutPixelDimensions,
        final Range<Double> range,
        final boolean hasLegend,
        final GradientInfo info
    );

    public abstract GraphPane createGraphPane(
        final PixelDimensions layoutPixelDimensions,
        final Range<Double> range,
        final boolean hasLegend
    );

    public abstract Group createLegendPane(
        final PixelDimensions layoutPixelDimensions,
        final String legend
    );
}
