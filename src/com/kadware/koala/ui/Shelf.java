/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui;

import com.kadware.koala.ui.panels.*;
import javafx.scene.layout.HBox;

public class Shelf extends HBox {

    public static Shelf createEmptyShelf(
        final int spaces,
        final boolean withOutputModule
    ) {
        var s = new Shelf();

        var remaining = spaces;
        Panel outputPanel = null;
        if (withOutputModule) {
            outputPanel = new StereoOutputPanel();
            remaining--;
        }

        {//TODO test
            s.getChildren().add(new SimpleLFOPanel());
            remaining--;
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

        if (outputPanel != null)
            s.getChildren().add(outputPanel);

        return s;
    }
}
