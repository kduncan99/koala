/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

import com.kadware.koala.Koala;
import javafx.scene.paint.Color;

public class StereoDBGraphPane extends GraphPane {

    private static final int MARGIN_PIXELS = 2;

    private final DBGraphPane _leftGraph;
    private final DBGraphPane _rightGraph;

    //  TODO
    //      Needs to look like this:
    //      -------------
    //      |clip |clip |
    //      -------------
    //      |left |right|
    //      |graph|graph|
    //      |     |     |
    //      -------------
    //      | Pk  | Hld |
    //      -------------
    //      The clip indicator on top is fed separately by setClip()
    //          If you click it, it clears - perhaps a momentary button of some sort?
    //      Pk is push on-off, enables fast mode in the left/right graphs
    //          For this (and the next) we need a push on-off illuminated button
    //      Hld enables peak-hold in the left/right graphs

    public StereoDBGraphPane(
        final OrientationType orientation,
        final Color color
    ) {
        super(Koala.DBFS_RANGE, orientation, color);

        _leftGraph = new DBGraphPane(orientation, color);
        _rightGraph = new DBGraphPane(orientation, color);
        getChildren().addAll(_leftGraph, _rightGraph);
    }

    @Override
    public void setPrefSize(
        final double width,
        final double height
    ) {
        super.setPrefSize(width, height);

        var subWidth = (width - 3 * MARGIN_PIXELS) / 2;
        _leftGraph.setPrefSize(subWidth, height);
        _leftGraph.setLayoutX(MARGIN_PIXELS);
        _rightGraph.setPrefSize(subWidth, height);
        _rightGraph.setLayoutX(subWidth + 2 * MARGIN_PIXELS);
    }

    //  Only invoke on ApplicationThread - value range is -30.0 to 0.0
    @Override
    public void setValue(
        final double value
    ) {
        _leftGraph.setValue(value);
        _rightGraph.setValue(value);
    }

    //  Only invoke on ApplicationThread - value range is -30.0 to 0.0
    public void setValues(
        final double leftValue,
        final double rightValue
    ) {
        _leftGraph.setValue(leftValue);
        _rightGraph.setValue(rightValue);
    }
}
