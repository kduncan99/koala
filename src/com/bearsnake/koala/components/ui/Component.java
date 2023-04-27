/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * A Component is some particular UI entity which may be an indicator, or an input of some kind, or both.
 * It has the ability to generate notification messages to any registered listener.
 * It will have a self-generated unique identifier which will accompany any message it sends.
 */
public abstract class Component extends Pane {

    private static final AtomicInteger _nextIdentifier = new AtomicInteger(0);

    private final int _identifier;
    private ContextMenu _contextMenu = null;

    protected Component() {
        _identifier = _nextIdentifier.getAndIncrement();
        setOnMouseDragged(this::mouseDragged);
        setOnMousePressed(this::mousePressed);
    }

    public int getIdentifier() { return _identifier; }
    protected void setContextMenu(final ContextMenu menu) { _contextMenu = menu; }

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
}
