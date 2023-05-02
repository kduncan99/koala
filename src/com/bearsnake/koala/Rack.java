/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import com.bearsnake.koala.modules.Module;
import com.bearsnake.koala.modules.elements.ports.DestinationPort;
import com.bearsnake.koala.modules.elements.ports.SourcePort;
import com.bearsnake.koala.modules.elements.ports.ActivePort;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

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
        //  TODO validate count and width

        _shelfContent = new VBox();
        _shelfContent.setBackground(Koala.BACKGROUND);
        _shelfContent.setPadding(Koala.STANDARD_INSETS);
        _shelfContent.setSpacing(INTER_SHELF_PIXELS);

        //  populate the VBox with a particular number of shelves.
        for (int sx = 0; sx < shelfCount; sx++) {
            var shelf = new Shelf(shelfWidth);
            _shelfContent.getChildren().add(shelf);
            _shelves.put(sx, shelf);
        }

        getChildren().add(_shelfContent);
        _driverThread.start();
    }

    /**
     * Must be called {n} times per second, where n is the system frequency.
     * Each Module must make any necessary adjustments to implement its functionality.
     * Because of the very fine resolution required for audio, it is expected that this
     * will be invoked by the same code which feeds the audio output (i.e., the SourceDataLine).
     * Alternatively, it could be driven by code which is capturing output to a file rather than to
     * real-time playback, in which case it frequency of invocation is not important.
     */
    public void advanceModules() {
        _shelves.values().forEach(Shelf::advanceModules);
    }

    /**
     * Invoked when discarding a Rack
     */
    public void close() {
        _driverThread.terminate();

        //  TODO remove all connections

        //  TODO remove all shelves
        _shelves.values().forEach(Shelf::close);
        _shelves.clear();
    }

    public synchronized boolean connectPorts(
        final SourcePort source,
        final DestinationPort destination
    ) {
        System.out.printf("Rack:connectPorts %s, %s\n", source.getName(), destination.getName());//TODO remove
        var c = new Connection(this, source, destination);
        if (!c.connect()) {
            return false;
        }
        _connections.add(c);
        return true;
    }

    public synchronized boolean disconnectPorts(
        final Connection connection
    ) {
        if (!connection.disconnect())
            return false;
        _connections.remove(connection);
        return true;
    }

    public synchronized boolean disconnectPorts(
        final SourcePort source,
        final DestinationPort destination
    ) {
        System.out.printf("Rack:disconnect %s, %s\n", source.getName(), destination.getName());//TODO remove
        var result = false;
        for (var c : _connections) {
            if (source.equals(c.getSource()) && destination.equals(c.getDestination())) {
                System.out.println("Found connection...");//TODO remove
                result = disconnectPorts(c);
                break;
            }
        }
        System.out.printf("  Returning %s\n", result);//TODO remove
        return false;
    }

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
        final @NotNull String candidate
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
}
