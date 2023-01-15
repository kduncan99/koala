/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.modules.Module;
import com.kadware.koala.ui.elements.BlankConnectorPane;
import javafx.scene.layout.GridPane;

public abstract class ModulePanel extends Panel {

    protected final GridPane _connectionsPane;
    protected final Module _module;

    public ModulePanel(
        final Module module,
        final PanelWidth panelWidth,
        final String caption
    ) {
        super(panelWidth, caption);
        _module = module;

        //_controlsPane = .... what?
        _connectionsPane = new GridPane();

        getConnectionsSection().getChildren().add(_connectionsPane);

        for (int vx = 0; vx < ConnectionsSection.VERTICAL_CELLS; ++vx) {
            for (int hx = 0; hx < getConnectionsSection()._horizontalCells; ++hx) {
                _connectionsPane.add(new BlankConnectorPane(), hx, vx);
            }
        }
        populateControls();
        populateConnections();
    }

    public abstract void populateControls();
    public abstract void populateConnections();
}
