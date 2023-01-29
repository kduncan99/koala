/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.DoubleRange;
import com.kadware.koala.IntegerRange;
import com.kadware.koala.RolloverCounter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.stream.IntStream;

public class SelectableGraphPane extends GraphPane {

    private final RolloverCounter _selector = new RolloverCounter(new IntegerRange(0, 4));
    private final GraphPane[] _graphPanes = new GraphPane[4];

    public SelectableGraphPane(
        final DoubleRange range,
        final OrientationType orientation,
        final Color color
    ) {
        super(range, orientation, color);

        _graphPanes[0] = new BarGraphPane(range, orientation, color);
        _graphPanes[1] = new DotGraphPane(range, orientation, color);
        _graphPanes[2] = new LineGraphPane(range, orientation, color);
        _graphPanes[3] = new SplitBarGraphPane(range, orientation, color);

        getChildren().addAll(_graphPanes);
        for (int gpx = 0; gpx < _graphPanes.length; ++gpx) {
            _graphPanes[gpx].setVisible(gpx == _selector.getValue());
        }

        setOnMouseClicked(this::mouseClicked);
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);
        int bound = _graphPanes.length;
        IntStream.range(0, bound).forEach(gpx -> {
            _graphPanes[gpx].setPrefSize(getPrefWidth(), getPrefHeight());
            _graphPanes[gpx].setValue(0.0);
        });
    }

    @Override
    public void setValue(double value) {
        _graphPanes[_selector.getValue()].setValue(value);
    }

    private void mouseClicked(
        final MouseEvent event
    ) {
        _graphPanes[_selector.getValue()].setVisible(false);
        _selector.increment();
        _graphPanes[_selector.getValue()].setVisible(true);
    }
}
