/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ports.IInputPort;
import javafx.scene.paint.Color;

//  TODO change this to the following:
//      -----
//      | a |
//      -----
//      | b |
//      -----
//  where a is the depiction of the jack
//    and b is the (shorter) caption
//  This gives us 2x the number of jacks per panel

/**
 * The InputConnector component is a square graphic entity consisting of the following divisions:
 *  ---------
 *  | a | b |
 *  ---------
 *  |   c   |
 *  ---------
 *  Where
 *      a: a right-pointing arrow
 *      b: a depiction of an electrical input of some sort
 *      c: a short caption/label, centered
 */
public class InputConnectorPane extends ConnectorPane {

    private static final Color CONNECTOR_COLOR = Color.BLUE;

    private final IInputPort _port;

    public InputConnectorPane(
        final String caption,
        final IInputPort port
    ) {
        super(caption, CONNECTOR_COLOR);
        _port = port;
    }
}
