/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.Koala;
import com.bearsnake.koala.Rack;
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
     * Connects a destination and a source port.
     * Should ONLY be invoked by Rack, for proper Wire handling.
     * @return true if successful, else false
     */
    public static synchronized boolean connect(
        final SourcePort source,
        final DestinationPort destination
    ) {
        if (!destination.connect(source)) {
            return false;
        }

        if (!source.connect(destination)) {
            destination.disconnect(source);
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
     * Disconnects a destination and a source port connection
     * Should ONLY be invoked by Rack, for proper Wire handling.
     */
    public static synchronized void disconnect(
        final SourcePort source,
        final DestinationPort destination
    ) {
        source.disconnect(destination);
        destination.disconnect(source);
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
        _connectMenu.getItems().clear();
        //  TODO add all candidate ports to this list

        _disconnectMenu.getItems().clear();
        for (var p : _connections) {
            var mi = new MenuItem(p.getName());
            mi.setUserData(p);
            mi.setOnAction(this::handleDisconnectMenuItem);
            _disconnectMenu.getItems().add(mi);
        }
    }

    private void handleDisconnectMenuItem(
        final ActionEvent event
    ) {
        var mi = (MenuItem) event.getSource();
        var port = (Port) mi.getUserData();
        var rack = Rack.findContainingRack(this);
        if (this instanceof DestinationPort) {
            rack.disconnectPorts((SourcePort) port, (DestinationPort) this);
        } else {
            rack.disconnectPorts((SourcePort) this, (DestinationPort) port);
        }
    }

    public abstract void repaint(); //  Can only be invoked on the Application thread
}
