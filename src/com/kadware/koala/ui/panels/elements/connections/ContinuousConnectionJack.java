/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.ContinuousPort;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * A statically-sized graphic element which depicts a physical input or output connection.
 */
public class ContinuousConnectionJack extends ConnectionJack {

    public static final Color RING_COLOR = Color.RED;
    public static final Color OVERLOAD_FALSE = Color.rgb(32, 16, 0);
    public static final Color OVERLOAD_TRUE = Color.rgb(255, 100, 0);

    private final ContinuousPort _port;
    private final PortIndicators _indicators;

    private static class PortIndicators extends VBox {

        private final Circle _overload;
        private final Circle _signal;

        public PortIndicators() {
            _overload = new Circle();
            _overload.setRadius(INDICATOR_RADIUS);
            _overload.setFill(OVERLOAD_FALSE);
            _signal = new Circle();
            _signal.setRadius(INDICATOR_RADIUS);
            _signal.setFill(Color.BLACK);
            getChildren().addAll(_overload, _signal);
        }
    }

    public ContinuousConnectionJack(
        //  TODO mouse properties
        final ContinuousPort port
    ) {
        super(RING_COLOR);

        _port = port;
        _indicators = new PortIndicators();
        getChildren().addAll(_indicators);
        //  TODO add double-click mouse property to clear the overload on the port
    }

    @Override
    public void paint() {
        _indicators._overload.setFill(_port.getOverload() ? OVERLOAD_TRUE : OVERLOAD_FALSE);

        double absValue = 0.0;
        if (_port instanceof ContinuousInputPort ip)
            absValue = Math.abs(ip.getValue());
        else if (_port instanceof ContinuousOutputPort op)
            absValue = Math.abs(op.getCurrentValue());
        double green = (absValue / Koala.MAX_CVPORT_VALUE) * 255.0;
        _indicators._signal.setFill(Color.rgb(0, (int)green, 0));
    }
}
