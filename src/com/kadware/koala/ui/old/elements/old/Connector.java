/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.elements.old;

import com.kadware.koala.ports.Port;
import com.kadware.koala.ui.old.panels.old.BasePanel;

import java.awt.*;

/**
 * A Connector is the graphical representation of a Port.
 * It contains two graphical entities, arranged vertically as:
 *   A JackContainer which represents either an input or an output jack
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
public class Connector extends Cell {

    private static final Dimension ICON_DIMENSION =
        new Dimension(CELL_WIDTH_PIXELS / 2, CELL_HEIGHT_PIXELS / 2);
    private static final Dimension PIN_JACK_DIMENSION =
        new Dimension(CELL_WIDTH_PIXELS / 2, CELL_HEIGHT_PIXELS / 2);
    private static final Dimension LABEL_DIMENSION =
        new Dimension(CELL_WIDTH_PIXELS, CELL_HEIGHT_PIXELS / 2);

    protected static abstract class PinJack extends Component {

        private final Color _color;
        private final Port _port;

        protected PinJack(
            final Color color,
            final Port port
        ) {
            _color = color;
            _port = port;

            setSize(PIN_JACK_DIMENSION);
            setPreferredSize(PIN_JACK_DIMENSION);
            setMinimumSize(PIN_JACK_DIMENSION);
            setMaximumSize(PIN_JACK_DIMENSION);
        }

        public Port getPort() {
            return _port;
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(_color);
            g.fillOval(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.fillOval(3, 3, getWidth() - 6, getHeight() - 6);
            super.paint(g);
        }
    }

    protected static abstract class JackContainer extends Container {

        public final Port _port;
        public final Label _icon;
        public final PinJack _pinJack;

        protected JackContainer(
            final Port port,
            final Color color,
            final PinJack pinJack
        ) {
            _port = port;
            _pinJack = pinJack;

            setLayout(new GridLayout(1, 2));

            _icon = new Label("➜");
            _icon.setFont(BasePanel.PANEL_FONT);
            _icon.setForeground(color);
            _icon.setBackground(BasePanel.PANEL_COLOR);
            _icon.setSize(ICON_DIMENSION);
            _icon.setPreferredSize(ICON_DIMENSION);
            _icon.setMinimumSize(ICON_DIMENSION);
            _icon.setMaximumSize(ICON_DIMENSION);
        }
    }

    protected Connector(
        final JackContainer jackContainer
    ) {
        setLayout(new GridLayout(2, 1));

        add(jackContainer);

        var label = new Label("In");
        label.setBackground(BasePanel.PANEL_COLOR);
        label.setFont(BasePanel.PANEL_FONT);
        label.setAlignment(Label.CENTER);
        label.setSize(LABEL_DIMENSION);
        label.setPreferredSize(LABEL_DIMENSION);
        label.setMinimumSize(LABEL_DIMENSION);
        label.setMaximumSize(LABEL_DIMENSION);
        add(label);
    }
}
