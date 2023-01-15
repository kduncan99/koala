package com.kadware.koala.ui.old.panels.old;

import com.kadware.koala.ui.old.BorderPanel;

import java.awt.*;

/**
 * The graphical area of the panel. It is separate from the BasePanel container
 * simply so that we may have some small margin around it.
 */
class ContentSubPanel extends Container {

    private static final int PIXELS_PER_MARGIN = 5;           //  this is the margin around the unit's edges
    private static final Insets MARGIN_INSETS =
        new Insets(PIXELS_PER_MARGIN, PIXELS_PER_MARGIN, PIXELS_PER_MARGIN, PIXELS_PER_MARGIN);

    final ConnectorsSubPanel _connectorsPanel;
    final ControlsSubPanel _controlsPanel;

    public ContentSubPanel(
        final PanelWidth panelWidth,
        final String topCaption
    ) {
        setLayout(new GridBagLayout());

        var title1 = new Label(topCaption);
        title1.setFont(BasePanel.PANEL_FONT);
        title1.setBackground(BasePanel.PANEL_COLOR);
        var constraints1 = new GridBagConstraints();
        constraints1.gridx = 0;
        constraints1.gridy = 0;
        constraints1.insets = MARGIN_INSETS;
        constraints1.gridwidth = 1;
        constraints1.gridheight = 1;
        add(title1, constraints1);

        _controlsPanel = new ControlsSubPanel(panelWidth);
        var constraints2 = new GridBagConstraints();
        constraints2.gridx = 0;
        constraints2.gridy = 1;
        constraints2.insets = MARGIN_INSETS;
        constraints2.gridwidth = 1;
        constraints2.gridheight = 1;
        var x = new BorderPanel(5);
        x.add(_controlsPanel);
        add(x, constraints2);

        _connectorsPanel = new ConnectorsSubPanel(panelWidth);
        var constraints3 = new GridBagConstraints();
        constraints3.gridx = 0;
        constraints3.gridy = 2;
        constraints3.insets = MARGIN_INSETS;
        constraints3.gridwidth = 1;
        constraints3.gridheight = 1;
        add(_connectorsPanel, constraints3);
    }
}
