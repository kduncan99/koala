/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.PixelDimensions;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * Base class for any UI Element.
 * An element is any graphical entity which serves a singular purpose.
 * Examples include
 *      Connections
 *      Controls
 *      Indicators
 * <p>
 * Indicators are categorized with controls, as there are controls which contain indicators, as well
 * as discrete controls and indicators.
 * An element encompasses one or more cells.
 * Generally, connection elements consist of a single cell, while control/indicator elements consist of
 * one or more cells in either or both directions (i.e., 1x1, 1x2, 3x1, 2x2, etc).
 * <p>
 * All elements are displayed with an inset graphical border.
 * All elements are slightly darker than the module background.
 */
public abstract class Element extends VBox {

    protected static final int INTER_CELL_SPACE = 5;  //  adjust as necessary

    protected final CellDimensions _cellDimensions;
    protected final PixelDimensions _pixelDimensions;
    private ContextMenu _contextMenu = null;

    public Element(
        final CellDimensions cellDimensions,
        final PixelDimensions pixelDimensions
    ) {
        _cellDimensions = cellDimensions;
        _pixelDimensions = pixelDimensions;

        setBorder(Koala.OUTSET_BORDER);

        setMaxSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());
        setMinSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());
        setPrefSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());

        var clipRect = new Rectangle(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());
        setClip(clipRect);

        setOnMouseDragged(this::mouseDragged);
        setOnMousePressed(this::mousePressed);
    }

    public CellDimensions getCellDimensions() { return _cellDimensions; }
    protected ContextMenu getContextMenu() { return _contextMenu; }
    public PixelDimensions getPixelDimensions() { return _pixelDimensions; }

    private void invokeMenu(
        final ActionEvent event
    ) {
        getContextMenu().show(this, 0.0, 0.0);
    }

    protected void mouseClicked(
        final MouseEvent event
    ) {
        //  The subclass overrides this if it cares about mouse clicks.
        //  This is NOT invoked for right-mouse-click if a context menu has been specified.
    }

    protected void mouseDoubleClicked(
        final MouseEvent event
    ) {
        //  The subclass overrides this if it wants to be notified of double-clicks.
    }

    protected void mouseDragged(
        final MouseEvent event
    ) {
        //  The subclass overrides this if it wants to be notified of drag events
    }

    private void mousePressed(
        final MouseEvent event
    ) {
        if (event.isSecondaryButtonDown() && (_contextMenu != null)) {
            _contextMenu.show(this, Side.RIGHT, 0, 0);
        } else {
            mouseClicked(event);
            if (event.getClickCount() == 2) {
                mouseDoubleClicked(event);
            }
        }
    }

    protected void setContextMenu(final ContextMenu menu) {
        _contextMenu = menu;
        _contextMenu.setOnAction(this::invokeMenu);
    }
}
