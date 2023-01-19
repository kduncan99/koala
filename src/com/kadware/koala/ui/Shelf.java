/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui;

import com.kadware.koala.ui.panels.*;
import javafx.scene.layout.HBox;

import java.util.Map;
import java.util.TreeMap;

public class Shelf extends HBox {

    private final Map<Integer, Panel> _panels = new TreeMap<>();

    public static Shelf createEmptyShelf(
        final int spaces,
        final boolean withOutputModule
    ) {
        var s = new Shelf();

        var sx = 0;
        var remaining = spaces;
        Panel outputPanel = null;
        if (withOutputModule) {
            outputPanel = new StereoOutputPanel();
            remaining--;
        }

        {//TODO test
            var p = new SimpleLFOPanel();
            s.getChildren().add(p);
            s._panels.put(sx++, p);
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
            s._panels.put(sx, panel);
            sx += panel.getPanelWidth()._spaceCount;
            remaining -= panel.getPanelWidth()._spaceCount;
        }

        if (outputPanel != null) {
            s.getChildren().add(outputPanel);
            s._panels.put(sx, outputPanel);
            sx += outputPanel.getPanelWidth()._spaceCount;
        }

        return s;
    }

    //  Only to be run on the Application thread
    public void repaint() {
        _panels.values().forEach(Panel::repaint);
    }
}
