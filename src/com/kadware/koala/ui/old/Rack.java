/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old;

import com.kadware.koala.modules.DualNoiseModule;
import com.kadware.koala.modules.Module;
import com.kadware.koala.modules.ModuleManager;
import com.kadware.koala.ui.old.panels.old.*;

import java.awt.*;

public class Rack extends Container {

    private int _spaces;

    private Rack(
        final int horizontalSpaces
    ) {
        _spaces = horizontalSpaces;
        setLayout(new GridBagLayout());
    }

    public static Rack createEmptyRack(
        final int horizontalSpaces
    ) {
        var rack = new Rack(horizontalSpaces);
        var remaining = horizontalSpaces;

        var xPos = 0;
        {//TODO testing
            var module = ModuleManager.createModule(Module.ModuleType.DualNoise);
            var panel = new DualNoisePanel((DualNoiseModule) module);
            var c = new GridBagConstraints();
            c.gridx = xPos;
            c.gridy = 0;
            c.gridheight = 1;
            c.gridwidth = panel.getPanelWidth()._spaceCount;
            rack.add(panel);

            remaining -= c.gridwidth;
            xPos += c.gridwidth;
        }//TODO end testing
        while (remaining > 0) {
            BlankPanel panel =
                switch (remaining) {
                    case 1 -> new BlankSinglePanel();
                    case 2 -> new BlankDoublePanel();
                    default -> new BlankTriplePanel();
                };

            var c = new GridBagConstraints();
            c.gridx = xPos;
            c.gridy = 0;
            c.gridheight = 1;
            c.gridwidth = panel.getPanelWidth()._spaceCount;
            rack.add(panel);

            remaining -= c.gridwidth;
            xPos += c.gridwidth;
        }

        return rack;
    }
}
