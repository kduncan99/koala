/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ports.DiscretePort;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * A statically-sized graphic element which depicts a physical input or output connection.
 */
public class DiscreteConnectionJack extends ConnectionJack {

    public static final int HORIZONTAL_EDGE_PIXELS = 40;
    public static final int VERTICAL_EDGE_PIXELS = 20;
    public static final Color RING_COLOR = Color.GREEN;

    private final Label _clipLabel;
    private final DiscretePort _port;

    public DiscreteConnectionJack(
        //  TODO mouse properties
        final DiscretePort port
    ) {
        super(RING_COLOR);

        _port = port;

        _clipLabel = new Label("▣");//"⦁");
        _clipLabel.setPrefSize(HORIZONTAL_EDGE_PIXELS / 2.0, VERTICAL_EDGE_PIXELS);
        _clipLabel.setTextFill(Color.BLACK);
        _clipLabel.setAlignment(Pos.BASELINE_CENTER);

        getChildren().addAll(_clipLabel);
        //  TODO add double-click mouse property to clear the overload on the port
    }

    @Override
    public void paint() {}
}
