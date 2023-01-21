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

    protected final GradientPane _gradientPane;
    protected final Pane _graphPane;
    protected final Group _legendPane;
    protected final PixelDimensions _pixelDimensions;

    protected LayoutPane(
        final CellDimensions cellDimensions,
        final Range<Double> range,
        final String legend,
        final GradientInfo info
    ) {
        _pixelDimensions = ControlEntityPane.toPixelDimensions(cellDimensions);
        setPrefSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());

        var hasLegend = !(legend == null || legend.isEmpty());
        _legendPane = createLegendPane(_pixelDimensions, legend);
        _graphPane = createGraphPane(_pixelDimensions, hasLegend);
        _gradientPane = createGradientPane(_pixelDimensions, range, hasLegend, info);
    }

    public abstract GradientPane createGradientPane(
        final PixelDimensions layoutPixelDimensions,
        final Range<Double> range,
        final boolean hasLegend,
        final GradientInfo info
    );

    public abstract Pane createGraphPane(
        final PixelDimensions layoutPixelDimensions,
        final boolean hasLegend
    );

    public abstract Group createLegendPane(
        final PixelDimensions layoutPixelDimensions,
        final String legend
    );
}
