/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.ContinuousInputPort;
import com.kadware.koala.ports.ContinuousOutputPort;
import com.kadware.koala.ports.ContinuousPort;
import com.kadware.koala.ui.components.AnalogLED;
import com.kadware.koala.ui.components.DigitalLED;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A statically-sized graphic element which depicts a physical input or output connection.
 */
public class ContinuousConnectionJack extends ConnectionJack {

    public static final Color RING_COLOR = Color.RED;
    public static final Color OVERLOAD_COLOR = Color.rgb(255, 100, 0);
    public static final Color SIGNAL_COLOR = Color.rgb(32, 255, 32);

    private final ContinuousPort _port;
    private final PortIndicators _indicators;

    private static class PortIndicators extends VBox {

        private final DigitalLED _overload;
        private final AnalogLED _signal;

        public PortIndicators() {
            _overload = new DigitalLED(INDICATOR_RADIUS, OVERLOAD_COLOR);
            _signal = new AnalogLED(INDICATOR_RADIUS, SIGNAL_COLOR, Koala.MIN_CVPORT_VALUE, Koala.MAX_CVPORT_VALUE);
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
    }

    @Override
    public void paint() {
        _indicators._overload.setValue(_port.getOverload());
        if (_port instanceof ContinuousInputPort ip)
            _indicators._signal.setValue(ip.getValue());
        else if (_port instanceof ContinuousOutputPort op)
            _indicators._signal.setValue(op.getCurrentValue());
    }
}
