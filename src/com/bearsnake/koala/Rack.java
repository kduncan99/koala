/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.util.TreeMap;

/**
 * A rack is a container of 1 or more vertically stacked Shelf objects.
 * There is one and only one Rack object for a given application instance.
 * The rack must have exactly one OutputModule, in order for any processing to occur.
 * It is suggested that the UI require either
 *  * specification of an output module type at startup, to be applied to the rack immediately upon starupt, or
 *  * disallowing the addition of any modules *other* than an OutputModule until one is added,
 *  AND
 *  * only one OutputModule can be added to a Rack.
 */
public class Rack extends VBox {

    private final TreeMap<Integer, Shelf> _shelves = new TreeMap<>();
    private boolean _terminate = false;
    private final DriverThread _driverThread = new DriverThread();

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

        setBackground(Koala.BACKGROUND);
        setPadding(Koala.STANDARD_INSETS);
        setSpacing(1.0);

        //  create a VBox consisting of a particular number of shelves.
        for (int sx = 0; sx < shelfCount; sx++) {
            var shelf = new Shelf(shelfWidth);
            getChildren().add(shelf);
            _shelves.put(sx, shelf);
        }

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

    //  Only to be run on the Application thread
    public void repaint() {
        _shelves.values().forEach(Shelf::repaint);
    }
}
