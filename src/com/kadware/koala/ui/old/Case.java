/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Case extends Frame {

    private static final int PIXELS_PER_MARGIN = 10;

    private static class Listener extends WindowAdapter {

        private final Frame _frame;

        public Listener(
            final Frame frame
        ) {
            _frame = frame;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            _frame.dispose();
        }
    }

    private Case() {
        super();
        setVisible(true);
//        setResizable(false);
        setTitle("Koala v1.0");
        addWindowListener(new Listener(this));
    }

    public static Case createEmptyCase(
        final int racks,
        final int spacesPerRack
    ) {
        //  TODO make sure racks and spacesPerRack are within reasonable limits

        var kase = new Case();
        kase.setLayout(new GridLayout(racks, 1));
        for (int r = 0; r < racks; ++r) {
            var rack = Rack.createEmptyRack(spacesPerRack);
            var border = new BorderPanel(PIXELS_PER_MARGIN, null, Color.BLACK);
            border.add(rack);
            kase.add(border);
        }
        kase.pack();
        return kase;
    }

    public static Case createEmptyCase() {
        return createEmptyCase(1, 10);
    }
}
