/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.Koala;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
public abstract class ActivePort extends Port {

    public static final double JACK_RADIUS =
        (double) Math.min(GRAPHIC_DIMENSIONS.getWidth(), GRAPHIC_DIMENSIONS.getHeight()) / 2.0 * 0.8;

    protected final ConcurrentLinkedQueue<ActivePort> _connections = new ConcurrentLinkedQueue<>();
    private final Menu _connectMenu;
    private final Menu _disconnectMenu;
    protected final Jack _jack;
    protected final Label _label;
    private final String _name;

    protected ActivePort(
        final String moduleName,
        final String name,
        final Jack jack,
        final String caption
    ) {
        setPrefSize(_pixelDimensions.getWidth(), _pixelDimensions.getHeight());

        _jack = jack;
        //TODO need to stretch label to fit containing vbox
        _label = new Label(caption);
        _label.setTextFill(Koala.LEGEND_COLOR);
        _label.setAlignment(Pos.CENTER);
        _name = moduleName + ":" + name;

        _connectMenu = new Menu("Connect");
        _disconnectMenu = new Menu("Disconnect");
        var cm = new ContextMenu();
        cm.getItems().addAll(_connectMenu, _disconnectMenu);
        cm.setOnAction(this::invokeMenu);
        cm.setOnShowing(this::updateContextMenuItems);
        setContextMenu(cm);
    }

    /**
     * Indicates whether this port can connect to some other port
     */
    public abstract boolean canConnectTo(final ActivePort partner);

    protected final boolean connect(
        final ActivePort partner
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
     * Should ONLY be invoked by Rack, for proper Wire handling.
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

    protected final void disconnect(
        final ActivePort partner
    ) {
        _connections.remove(partner);
    }

    /**
     * Disconnects an input and output port connection
     * Should ONLY be invoked by Rack, for proper Wire handling.
     */
    public static synchronized void disconnect(
        final OutputPort output,
        final InputPort input
    ) {
        output.disconnect(input);
        input.disconnect(output);
    }

    public Point2D getJackCenterSceneCoordinates() {
        return _jack.getCenterSceneCoordinates();
    }

    /**
     * Returns number of connected ports
     */
    public final int getConnectionCount() {
        return _connections.size();
    }

    public final Jack getJack() { return _jack; }
    public final String getName() { return _name; }
    public abstract Color getWireColor();

    private void invokeMenu(
        final ActionEvent event
    ) {
        getContextMenu().show(this, 0.0, 0.0);
    }

    private void updateContextMenuItems(
        final Event event
    ) {
        //TODO
        _connectMenu.getItems().clear();
        var mi0 = new MenuItem("Fee");
        var mi1 = new MenuItem("Fie");
        _connectMenu.getItems().addAll(mi0, mi1);

        _disconnectMenu.getItems().clear();
        for (var p : _connections) {
            var mi = new MenuItem(p.getName());
            mi.setOnAction((e)->{});
            _disconnectMenu.getItems().add(mi);
        }
    }

    public abstract void repaint(); //  Can only be invoked on the Application thread
}
