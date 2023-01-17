/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui;

import javafx.scene.layout.VBox;

public class Rack extends VBox {

    private static final int MARGIN_PIXELS = 5;

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
        }

        r.setVisible(true);
        return r;
    }
}
