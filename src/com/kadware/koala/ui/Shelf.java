/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui;

import com.kadware.koala.ui.panels.BlankPanel;
import com.kadware.koala.ui.panels.PanelWidth;
import com.kadware.koala.ui.panels.StereoOutputPanel;
import javafx.scene.layout.HBox;

public class Shelf extends HBox {

    private static final int MARGIN_PIXELS = 5;

    private Shelf() {
        super(MARGIN_PIXELS);
    }

    public static Shelf createEmptyShelf(
        final int spaces
    ) {
        var s = new Shelf();

        var remaining = spaces;
        {//TODO testing
            var panel = new StereoOutputPanel();
            s.getChildren().add(panel);
            remaining -= panel.getPanelWidth()._spaceCount;
        }
        while (remaining > 0) {
            var panel =
                switch (remaining) {
                    case 1 -> new BlankPanel(PanelWidth.SINGLE);
                    case 2 -> new BlankPanel(PanelWidth.DOUBLE);
                    default -> new BlankPanel(PanelWidth.TRIPLE);
                };
            s.getChildren().add(panel);
            remaining -= panel.getPanelWidth()._spaceCount;
        }

        s.setVisible(true);
        return s;
    }
}
