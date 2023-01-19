/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ports.IInputPort;

/**
 * The InputConnectionPane is a square graphic entity consisting of the following divisions:
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
public class InputConnectionPane extends ConnectionPane {

    private final IInputPort _port;

    public InputConnectionPane(
        final String caption,
        final IInputPort port
    ) {
        super(caption, port.getPort());
        _port = port;
    }

    @Override
    public void paint() {
        //TODO
    }
}
