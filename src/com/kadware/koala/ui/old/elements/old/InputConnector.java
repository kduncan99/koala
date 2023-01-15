/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.elements.old;

import com.kadware.koala.ports.IInputPort;
import com.kadware.koala.ports.Port;

import java.awt.*;

/**
 * An InputConnector is the graphical representation of a Port.
 * It contains two graphical entities, arranged vertically as:
 *   A JackContainer which represents either an input jack
 *   A caption which is a short 3- or 4- character description of the connector
 *
 * The JackContainer contains two entities arranged horizontally:
 *   A single-character graphic which is always a right-arrow character
 *   A graphic of an actual pin-jack
 * Both graphics are of a particular color which has no meaning to this class, but which might
 * be semantically significant at some higher level.
 * The input JackContainer has the arrow left of the pin-jack indicating signal entering the jack,
 * while the output JackContainer has the arrow to the right, indicating signal exiting the jack.
 *
 * The entire Connector is graphically represented as a square region.
 * The length parameter dictates the overall size as well as the sizes of the contained components.
 */
public class InputConnector extends Connector {

    protected static class InputPinJack extends PinJack {

        private final IInputPort _inputPort;

        protected InputPinJack(
            final Color color,
            final IInputPort inputPort
        ) {
            super(color, inputPort.getPort());
            _inputPort = inputPort;
        }

        //  TODO register to listen for left- and right- clicks
    }

    protected static class InputJackContainer extends JackContainer {

        protected InputJackContainer(
            final Port port,
            final Color color,
            final PinJack pinJack
        ) {
            super(port, color, pinJack);
            add(_icon);
            add(pinJack);
        }
    }

    public InputConnector(
        final Color color,
        final IInputPort inputPort
    ) {
        super(new InputJackContainer(inputPort.getPort(), color, new InputPinJack(color, inputPort)));
    }
}
