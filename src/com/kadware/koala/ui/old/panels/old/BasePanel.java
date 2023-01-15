/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.old.panels.old;

import java.awt.*;


public abstract class BasePanel extends Container {

    private static final int PIXELS_PER_MARGIN = 5;           //  this is the margin around the unit's edges
    protected static final int HORIZONTAL_CELLS_SINGLE = 2;     //  a single panel is 2 cells wide
    protected static final int HORIZONTAL_CELLS_DOUBLE = 4;     //  a double panel is 4 cells wide with a little slop
    protected static final int HORIZONTAL_CELLS_TRIPLE = 6;     //  a triple panel is 7 cells wide
//    protected static final int VERTICAL_CONTROL_CELLS = 6;      //  number of cells high for controls and indicators
//    protected static final int VERTICAL_CONNECTION_CELLS = 2;         //  number of cells high for ports
//
//    private static final Insets MARGIN_INSETS =
//        new Insets(PIXELS_PER_MARGIN, PIXELS_PER_MARGIN, PIXELS_PER_MARGIN, PIXELS_PER_MARGIN);

    public static final Color PANEL_COLOR = new Color(192, 192, 192);
    public static final Font PANEL_FONT = new Font(java.awt.Font.SANS_SERIF, Font.PLAIN, 12);

    final ContentSubPanel _content;
    final PanelWidth _panelWidth;

    public BasePanel(
        final PanelWidth panelWidth,
        final String caption
    ) {
        _panelWidth = panelWidth;
        _content = new ContentSubPanel(panelWidth, caption);
        add(_content);
    }

//    protected ConnectorsSubPanel getConnectorsPanel() { return _contentPanel._connectorsPanel; }
//    protected ControlsSubPanel getControlsPanel() { return _contentPanel._controlsPanel; }
    public PanelWidth getPanelWidth() { return _panelWidth; }
}
