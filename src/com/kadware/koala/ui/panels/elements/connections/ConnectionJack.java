/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ports.ContinuousPort;
import com.kadware.koala.ports.DiscretePort;
import com.kadware.koala.ports.LogicPort;
import com.kadware.koala.ports.Port;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A statically-sized graphic element which depicts a physical input or output connection.
 * -----
 * |a|b|
 * -----
 *
 * a: graphic depiction of an input or output jack with a colored ring
 * b: one or more mini-indicators - the meaning of the indicator(s) depends on the subclass
 *
 * We build 'a', the subclass builds 'b'
 */
public abstract class ConnectionJack extends HBox {

    public static final int HORIZONTAL_EDGE_PIXELS = 40;
    public static final int VERTICAL_EDGE_PIXELS = 20;
    public static final int INDICATOR_RADIUS = 4;

    protected ConnectionJack(
        final Color ringColor
        //  TODO mouse properties
    ) {
        setPrefSize(HORIZONTAL_EDGE_PIXELS, VERTICAL_EDGE_PIXELS);
        setMaxSize(HORIZONTAL_EDGE_PIXELS, VERTICAL_EDGE_PIXELS);
        setMinSize(HORIZONTAL_EDGE_PIXELS, VERTICAL_EDGE_PIXELS);

        var radius = VERTICAL_EDGE_PIXELS * 0.35;
        var jack = new Circle();
        jack.setRadius(radius);
        jack.setStroke(ringColor);
        jack.setStrokeWidth(4.0);
        jack.setFill(Color.BLACK);
        getChildren().addAll(jack);

        //  TODO onMouseDragEnteredProperty
        //  TODO onMouseDragReleasedProperty
        //  TODO onMousePressedProperty
        //  TODO mouse double-click should invoke reset() on the port
    }

    public static ConnectionJack createConnectionJack(
        final Port port
    ) {
        if (port instanceof ContinuousPort cp) {
            return new ContinuousConnectionJack(cp);
        } else if (port instanceof LogicPort lp) {
            //  this has to come first, since LogicPort is a subclass of DiscretePort
            return new LogicConnectionJack(lp);
        } else if (port instanceof DiscretePort dp) {
            return new DiscreteConnectionJack(dp);
        } else {
            throw new RuntimeException("Unknown port type");
        }
    }

    //  Only to be called on the Application thread
    public abstract void paint();
}
