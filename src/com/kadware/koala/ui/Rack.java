/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui;

import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

public class Rack extends VBox {

    private static final int MARGIN_PIXELS = 5;

    private final Set<Shelf> _shelves = new HashSet<>();

    private Rack() {
        super(MARGIN_PIXELS);
    }

    public static Rack createEmptyRack(
        final int shelves,
        final int spaces
    ) {
        var r = new Rack();

        for (int sx = 0; sx < shelves; ++sx) {
            var s = Shelf.createEmptyShelf(spaces, sx == 0);
            r.getChildren().add(s);
            r._shelves.add(s);
        }

        r.setVisible(true);
        return r;
    }

    //  Only to be run on the Application thread
    public void repaint() {
        _shelves.forEach(Shelf::repaint);
    }
}
