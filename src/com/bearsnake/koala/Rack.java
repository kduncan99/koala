/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import com.bearsnake.koala.modules.elements.ports.InputPort;
import com.bearsnake.koala.modules.elements.ports.OutputPort;
import java.util.HashSet;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.TreeMap;

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
        final OutputPort source,
        final InputPort destination
    ) {
        var c = new Connection(this, source, destination);
        if (!c.connect()) {
            return false;
        }
        _connections.add(c);
        return true;
    }

    public synchronized boolean disconnect(
        final Connection connection
    ) {
        if (!connection.disconnect())
            return false;
        _connections.remove(connection);
        return true;
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
        return null;
    }

    public Group getRootGroup() { return (Group) getParent(); }

    //  Only to be run on the Application thread
    public void repaint() {
        _shelves.values().forEach(Shelf::repaint);
    }
}
