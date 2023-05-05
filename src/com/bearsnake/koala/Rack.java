/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import com.bearsnake.koala.modules.Module;
import com.bearsnake.koala.modules.elements.ports.DestinationPort;
import com.bearsnake.koala.modules.elements.ports.Port;
import com.bearsnake.koala.modules.elements.ports.SourcePort;
import com.bearsnake.koala.modules.elements.ports.ActivePort;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * A rack is a container of 1 or more vertically stacked Shelf objects.
 */
public class Rack extends Pane {

    private class DriverThread extends Thread {

        private boolean _terminate = false;
        private boolean _terminated = false;

        public void run() {
            while (!_terminate) {
                advanceModules();
                try {
                    Thread.sleep(0);
                } catch (InterruptedException ex) {
                    //  do nothing
                }
            }

            _terminated = true;
        }

        public void terminate() {
            _terminate = true;
            while (_terminated) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    //  nothing
                }
            }
        }
    }

    private static final int INTER_SHELF_PIXELS = 4;

    private final HashSet<Connection> _connections = new HashSet<>();
    private ActivePort _pendingConnectionInitialPort = null;
    private PendingWire _pendingWire = null;
    private final VBox _shelfContent;
    private final TreeMap<Integer, Shelf> _shelves = new TreeMap<>();
    private boolean _terminate = false;
    private final DriverThread _driverThread = new DriverThread();


    /**
     * Creates a Rack object
     * @param shelfCount Number of shelves for this rack
     * @param shelfWidth number of cells per shelf
     */
    public Rack(
        final int shelfCount,
        final int shelfWidth
    ) {
        if ((shelfCount < 1) || (shelfCount > 3)) {
            throw new RuntimeException("Invalid shelf count");
        }
        if ((shelfWidth < 5) || (shelfWidth > 20)) {
            throw new RuntimeException("Invalid shelf width");
        }

        _shelfContent = new VBox();
        _shelfContent.setBackground(Koala.BACKGROUND);
        _shelfContent.setPadding(Koala.STANDARD_INSETS);
        _shelfContent.setSpacing(20.0);//TODO INTER_SHELF_PIXELS);

        //  populate the VBox with a particular number of shelves.
        for (int sx = 0; sx < shelfCount; sx++) {
            var shelf = new Shelf(shelfWidth);
            _shelfContent.getChildren().add(shelf);
            _shelves.put(sx, shelf);
        }

        getChildren().add(_shelfContent);
        _driverThread.start();

        setOnMouseClicked(this::mouseClicked);
        setOnMouseMoved(this::mouseMoved);
    }

    /**
     * Must be called {n} times per second, where n is the system frequency.
     * Each Module must make any necessary adjustments to implement its functionality.
     * Because of the very fine resolution required for audio, it is expected that this
     * will be invoked by the same code which feeds the audio output (i.e., the SourceDataLine).
     * Alternatively, it could be driven by code which is capturing output to a file rather than to
     * real-time playback, in which case it frequency of invocation is not important.
     */
    public synchronized void advanceModules() {
        _shelves.values().forEach(Shelf::advanceModules);
    }

    /**
     * Invoked when discarding a Rack
     */
    public synchronized void close() {
        _driverThread.terminate();

        //  remove all connections
        while (!_connections.isEmpty()) {
            disconnectPorts(_connections.iterator().next());
        }

        //  remove all shelves
        //  TODO probably a better way to do this
        //      for each shelf, Shelf::close
        //      while (!_shelves.isEmpty())
        //          removeShelf(_shelves.iterator().next());
        _shelves.values().forEach(Shelf::close);
        _shelves.clear();
    }

    /**
     * Connects a source port and a destination port.
     * This is the ONLY method which should be invoked by any other higher-level code for producing this behavior...
     * This is due to the fact that the rack sits at the top of the hierarchy, and there is relevant state
     * which is contained in multiple different locations, including this class.
     * @param source source port to be connected
     * @param destination destination port to be connected
     * @return true if the connection was successfully made, else false
     */
    public synchronized boolean connectPorts(
        final SourcePort source,
        final DestinationPort destination
    ) {
        var c = new Connection(this, source, destination);
        if (!c.connect()) {
            return false;
        }
        _connections.add(c);
        return true;
    }

    /**
     * Convenience method for callers who aren't sure which port is source, and which is destination
     * (and don't want to have to figure it out).
     * @return true if the connection was successfully made, else false
     */
    public boolean connectPorts(
        final Port port1,
        final Port port2
    ) {
        if ((port1 instanceof SourcePort source) && (port2 instanceof DestinationPort destination)) {
            return connectPorts(source, destination);
        } else if ((port1 instanceof DestinationPort destination) && (port2 instanceof SourcePort source)) {
            return connectPorts(source, destination);
        } else {
            return false;
        }
    }

    /**
     * Disconnects a source port from a destination port.
     * The discussion for connectPorts() above applies here.
     * @param connection the connection to be removed
     * @return true if successful, else false
     */
    public synchronized boolean disconnectPorts(
        final Connection connection
    ) {
        if (!connection.disconnect())
            return false;
        _connections.remove(connection);
        return true;
    }

    /**
     * Disconnects a source port from a destination port.
     * This method is used if the caller knows the ports, but not the corresponding connection.
     * @param source source port to be disconnected
     * @param destination destination port to be disconnected
     * @return true if successful, else false
     */
    public synchronized boolean disconnectPorts(
        final SourcePort source,
        final DestinationPort destination
    ) {
        var result = false;
        for (var c : _connections) {
            if (source.equals(c.getSource()) && destination.equals(c.getDestination())) {
                result = disconnectPorts(c);
                break;
            }
        }
        return false;
    }

    /**
     * Convenience method for callers who aren't sure which port is source, and which is destination
     * (and don't want to have to figure it out).
     * @return true if successful, else false
     */
    public boolean disconnectPorts(
        final Port port1,
        final Port port2
    ) {
        if ((port1 instanceof SourcePort source) && (port2 instanceof DestinationPort destination)) {
            return disconnectPorts(source, destination);
        } else if ((port1 instanceof DestinationPort destination) && (port2 instanceof SourcePort source)) {
            return disconnectPorts(source, destination);
        } else {
            return false;
        }
    }

    /**
     * Allows the current Rack object to be located up the parent stack,
     * given a starting point of any other Node below the Rack.
     * @param child starting point
     * @return reference to Rack object
     */
    public static Rack findContainingRack(
        final Node child
    ) {
        Node n = child;
        while (n != null) {
            if (n instanceof Rack) {
                return (Rack) n;
            }
            n = n.getParent();
        }
        throw new RuntimeException("findContainingRack() invoked on an unowned node");
    }

    /**
     * Given a candidate name, we modify *if necessary* to make it unique among
     * all the modules in this rack.
     * @param candidate candidate name - should be just a few alpha characters, no digits
     * @return possibly-modified name
     */
    public synchronized String generateUniqueModuleName(
        final String candidate
    ) {
        var moduleNames = getAllModuleNames();
        if (!moduleNames.contains(candidate)) {
            return candidate;
        }
        for (int n = 2; ; n++) {
            var result = String.format("%s%d", candidate, n);
            if (!moduleNames.contains(result)) {
                return result;
            }
        }
    }

    /**
     * Retrieves a hash set of the current names of all the modules in all the shelves in this rack.
     */
    public synchronized HashSet<String> getAllModuleNames() {
        return _shelves.values()
                       .stream()
                       .flatMap(s -> s.getAllModuleNames().stream())
                       .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Retrieves a collection of all the ports from all the modules in the shelves of this rack
     */
    public synchronized Collection<ActivePort> getAllPorts() {
        return _shelves.values()
                       .stream()
                       .flatMap(s -> s.getAllPorts().stream())
                       .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Returns a reference to the Group which contains this rack.
     */
    public Group getRootGroup() { return (Group) getParent(); }

    //  Only to be run on the Application thread
    public void repaint() {
        _shelves.values().forEach(Shelf::repaint);
    }

    private void mouseClicked(
        final MouseEvent event
    ) {
        //  Ignore regular mouse clicks, but right-click terminates a pending connection
        if ((event.getButton() == MouseButton.SECONDARY) && (_pendingConnectionInitialPort != null)) {
            var group = (Group) getParent();
            group.getChildren().remove(_pendingWire);
            _pendingWire = null;
            _pendingConnectionInitialPort = null;
        }
    }

    private void mouseMoved(
        final MouseEvent event
    ) {
        //  If there is a connection pending, make the end of the pending wire follow the cursor
        if (_pendingConnectionInitialPort != null) {
            var group = (Group) getParent();
            var point = group.sceneToLocal(event.getSceneX(), event.getSceneY());
            _pendingWire.updateTerminalPoint(point);
        }
    }

    /**
     * An active port has been the target of a mouse click.
     * This could be the start or end of a connection attempt, or it could be nothing.
     * Figure it out.
     * @param event instigating event
     * @param port port which was clicked
     */
    public synchronized void portMouseClicked(
        final MouseEvent event,
        final ActivePort port
    ) {
        if (_pendingConnectionInitialPort == null) {
            //  No connection is pending - start one
            _pendingConnectionInitialPort = port;
            var group = (Group) getParent();
            var point = group.sceneToLocal(port.getJackCenterSceneCoordinates());
            _pendingWire = new PendingWire(point);
            group.getChildren().add(_pendingWire);
        } else {
            //  There *is* a pending connection - complete it?
            if (_pendingConnectionInitialPort.canConnectTo(port)) {
                if (connectPorts(_pendingConnectionInitialPort, port)) {
                    var group = (Group) getParent();
                    group.getChildren().remove(_pendingWire);
                    _pendingWire = null;
                    _pendingConnectionInitialPort = null;
                }
            }
        }
    }

    /**
     * Places a fully-constructed module into the indicated rack and shelf
     * @param shelfIndex Index of the shelf
     * @param location Location point within the shelf
     * @param module Module to be added
     * @return true if successful, else false
     */
    public synchronized boolean placeModule(
        final int shelfIndex,
        final int location,
        final Module module
    ) {
        if (shelfIndex >= _shelves.size()) {
            throw new RuntimeException("placeModule() invoked with invalid shelfIndex " + shelfIndex);
        }

        var adjustedName = generateUniqueModuleName(module.getName());
        module.setName(adjustedName);
        _shelves.get(shelfIndex).placeModule(location, module);
        return true;
    }

    public synchronized void createShelf(
        final int shelfWidth
    ) {
        var shelf = new Shelf(shelfWidth);
        _shelfContent.getChildren().add(shelf);
        _shelves.put(_shelves.size(), shelf);
    }

    public synchronized boolean deleteShelfAt(
        final int position
    ) {
        if (position >= _shelves.size()) {
            return false;
        }

        var s = _shelves.get(position);
        if (s.getChildren().size() > 0) {
            return false;
        }

        _shelfContent.getChildren().clear();

        int plimit = _shelves.size() - 1;
        for (int px = position; px < plimit - 1; px++ ){
            _shelves.put(px, _shelves.get(px + 1));
        }
        _shelves.remove(_shelves.size() - 1);

        for (var sh : _shelves.values()) {
            _shelfContent.getChildren().add(sh);
        }

        return true;
    }

    public synchronized int getShelfCount() {
        return _shelves.size();
    }
}
