/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.modules.elements.Element;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * A Connection is implemented graphically as a cell in the ConnectionsSection object.
 * Functionally, it represents either an input or an output for signal flow.
 * It is represented as:
 *  -----------
 *  | graphic |
 *  -----------
 *  |  label  |
 *  -----------
 * Where graphic is a pane or some such provided by the subclass.
 */
public abstract class Port extends Element {

    private static final CellDimensions CELL_DIMENSIONS = new CellDimensions(1, 1);
    private static final PixelDimensions PIXEL_DIMENSIONS = determinePixelDimensions(CELL_DIMENSIONS);
    public static final int HORIZONTAL_PIXELS_PER_CELL = 40;
    public static final int VERTICAL_PIXELS_PER_CELL = 40;

    public static final PixelDimensions GRAPHIC_DIMENSIONS =
        new PixelDimensions(HORIZONTAL_PIXELS_PER_CELL, VERTICAL_PIXELS_PER_CELL / 2);

    public static final double JACK_RADIUS =
        (double) Math.min(GRAPHIC_DIMENSIONS.getWidth(), GRAPHIC_DIMENSIONS.getHeight()) / 2.0 * 0.8;

    protected final ConcurrentLinkedQueue<Port> _connections = new ConcurrentLinkedQueue<>();
    private final ContextMenu _contextMenu;
    protected final Jack _jack;
    protected final Label _label;
    protected boolean _overloadIndicator;

    protected Port(
        final Jack jack,
        final String caption
    ) {
        super(CELL_DIMENSIONS, PIXEL_DIMENSIONS);
        setPrefSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());

        _jack = jack;
        //TODO need to stretch label to fit containing vbox
        _label = new Label(caption);
        _label.setTextFill(Koala.LEGEND_COLOR);
        _label.setAlignment(Pos.CENTER);

        setOnMousePressed(this::mousePressed);
        setOnDragDetected((e)-> startFullDrag());
        /*
            Lifecycle as follows:
            Port 1710051277 Mouse Drag Entered
            Port 1710051277 Mouse Drag Exited
            Port 1316922069 Mouse Drag Entered
            Port 1316922069 Mouse Drag Released
            Port 1316922069 Mouse Drag Exited
         */
        setOnMouseDragEntered((e)-> System.out.printf("Port %d Mouse Drag Entered\n", hashCode()));
        setOnMouseDragExited((e)-> System.out.printf("Port %d Mouse Drag Exited\n", hashCode()));
        setOnMouseDragReleased((e)-> System.out.printf("Port %d Mouse Drag Released\n", hashCode()));
        _contextMenu = createContextMenu();
    }

    protected Port() {
        //  For BlankPort
        super(CELL_DIMENSIONS, PIXEL_DIMENSIONS);
        setPrefSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());

        _jack = null;
        _label = null;
        _contextMenu = null;
    }

    private static ContextMenu createContextMenu() {
        var cm = new ContextMenu();

        var mi1 = new MenuItem("Disconnect All");
        cm.getItems().add(mi1);
        //TODO need to set up an action for mi1

        return cm;
    }

    /**
     * Indicates whether this port can connect to some other port
     */
    public abstract boolean canConnectTo(final Port partner);

    protected final boolean connect(
        final Port partner
    ) {
        if (canConnectTo(partner) && !_connections.contains(partner)) {
            _connections.add(partner);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Connects an input and an output port.
     * @return true if successful, else false
     */
    public static synchronized boolean connect(
        final OutputPort output,
        final InputPort input
    ) {
        if (!input.connect(output)) {
            return false;
        }

        if (!output.connect(input)) {
            input.disconnect(output);
            return false;
        }

        return true;
    }

    /**
     * Determines the dimensions of a prospective port in pixels given the cell dimensions of that port.
     * Generally, a Port is always 1x1, but we'll do this anyway, just in case.
     * @param cellDimensions cell dimensions of a port
     * @return pixel dimension object
     */
    public static PixelDimensions determinePixelDimensions(
        final CellDimensions cellDimensions
    ) {
        var width = cellDimensions.getWidth() * HORIZONTAL_PIXELS_PER_CELL;
        var height = cellDimensions.getHeight() * VERTICAL_PIXELS_PER_CELL;
        return new PixelDimensions(width, height);
    }

    protected final void disconnect(
        final Port partner
    ) {
        _connections.remove(partner);
    }

    /**
     * Disconnects an input and output port connection
     */
    public synchronized void disconnect(
        final OutputPort output,
        final InputPort input
    ) {
        output.disconnect(input);
        input.disconnect(output);
    }

    /**
     * Disconnect all connected ports from this port
     */
    public synchronized void disconnectAll() {
        for (var port : _connections) {
            port.disconnect(this);
        }
        _connections.clear();
    }

    /**
     * Returns number of connected ports
     */
    public final int getConnectionCount() {
        return _connections.size();
    }

    public final Jack getJack() { return _jack; }

    /**
     * Indicates whether the overload indicator on this port is flagged
     */
    public final boolean getOverloadIndicator() { return _overloadIndicator; }

    private void mousePressed(
        final MouseEvent event
    ) {
        if (event.isSecondaryButtonDown()) {
            System.out.println("Port context menu");
            _contextMenu.show(this, Side.RIGHT, 0, 0);
        }
    }

    public abstract void repaint(); //  Can only be invoked on the Application thread

    /**
     * Sets or clears the overload indicator
     */
    public final void setOverloadIndicator(
        final boolean value
    ) {
        _overloadIndicator = value;
        if (value) {
            _jack.setOverload();
        } else {
            _jack.clearOverload();
        }
    }
}
