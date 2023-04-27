/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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
