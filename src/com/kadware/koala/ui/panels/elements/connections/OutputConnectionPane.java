/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ports.IOutputPort;

/**
 * The OutputConnectionPane is a square graphic entity consisting of the following divisions:
 *  ---------
 *  | a | b |
 *  ---------
 *  |   c   |
 *  ---------
 *  Where
 *      a: a depiction of an electrical output of some sort
 *      b: a right-pointing arrow
 *      c: a short caption/label, centered
 */
public class OutputConnectionPane extends ConnectionPane {

    private final IOutputPort _port;

    public OutputConnectionPane(
        final String caption,
        final IOutputPort port
    ) {
        super(caption, port.getPort());
        _port = port;
    }

    @Override
    public void repaint() {
        //TODO
    }
}
