/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ports.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A statically-sized graphic element which depicts a physical input or output connection.
 */
public class LogicConnectionJack extends ConnectionJack {

    public static final Color RING_COLOR = Color.LIGHTBLUE;
    public static final Color SIGNAL_FALSE = Color.rgb(0, 32, 0);
    public static final Color SIGNAL_TRUE = Color.rgb(0, 255, 0);

    private final LogicPort _port;
    private final PortIndicators _indicators;

    private static class PortIndicators extends VBox {

        private final Circle _signal;

        public PortIndicators() {
            _signal = new Circle();
            _signal.setRadius(INDICATOR_RADIUS);
            _signal.setFill(Color.BLACK);
            getChildren().addAll(_signal);
        }
    }

    public LogicConnectionJack(
        //  TODO mouse properties
        final LogicPort port
    ) {
        super(RING_COLOR);

        _port = port;
        _indicators = new PortIndicators();
        getChildren().addAll(_indicators);
        //  TODO add double-click mouse property to clear the overload on the port
    }


    @Override
    public void paint() {
        if (_port instanceof LogicInputPort ip)
            _indicators._signal.setFill(ip.getValue() ? SIGNAL_TRUE : SIGNAL_FALSE);
        else if (_port instanceof LogicOutputPort op)
            _indicators._signal.setFill(op.getCurrentValue() ? SIGNAL_TRUE : SIGNAL_FALSE);
    }
}
