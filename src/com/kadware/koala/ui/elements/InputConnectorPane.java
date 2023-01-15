/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.elements;

import com.kadware.koala.ports.IInputPort;
import javafx.scene.paint.Color;

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
        super(caption, CONNECTOR_COLOR, GraphicPosition.GRAPHIC_ON_LEFT);
        _port = port;
    }
}
