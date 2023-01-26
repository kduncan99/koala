/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.IntegerRange;
import com.kadware.koala.RolloverCounter;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MultiGraphPane extends GraphPane {

    private final RolloverCounter _selector = new RolloverCounter(new IntegerRange(0, 4));
    private final GraphPane[] _graphPanes = new GraphPane[4];

    public MultiGraphPane(
        final SplitBarGraphParams params
    ) {
        super(params);

        BarGraphPane _barPane = new BarGraphPane(params);
        DotGraphPane _dotPane = new DotGraphPane(params);
        LineGraphPane _linePane = new LineGraphPane(params);
        SplitBarGraphPane _splitPane = new SplitBarGraphPane(params);

        _graphPanes[0] = _barPane;
        _graphPanes[1] = _dotPane;
        _graphPanes[2] = _linePane;
        _graphPanes[3] = _splitPane;
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
        IntStream.range(0, _graphPanes.length)
                 .forEach(gpx -> _graphPanes[gpx].setPrefSize(getPrefWidth(), getPrefHeight()));
    }

    @Override
    public void setValue(double value) {
        Arrays.stream(_graphPanes).forEach(gp -> gp.setValue(value));
    }

    private void mouseClicked(
        final MouseEvent event
    ) {
        _graphPanes[_selector.getValue()].setVisible(false);
        _selector.increment();
        _graphPanes[_selector.getValue()].setVisible(true);
    }
}
