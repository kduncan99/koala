/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

import com.bearsnake.koala.modules.BlankModule;
import com.bearsnake.koala.modules.Module;
import javafx.scene.layout.HBox;
import java.util.Map;
import java.util.TreeMap;

/**
 * A shelf is a virtualized container of 1 row of Module objects
 */
public class Shelf extends HBox {

    //  The key is the geographic index of the module within the shelf.
    //  The pixel x-coordinate of the module is calculated as key * CELL_PIXELS_WIDTH.
    protected final Map<Integer, Module> _modules = new TreeMap<>();

    /**
     * c'tor - we populate the shelf with a one-space BlankModule for the entire width of the shelf.
     * @param shelfWidth number of module spaces in this shelf
     */
    public Shelf(
        final int shelfWidth
    ) {
        //  TODO validate width

        setPadding(Koala.STANDARD_INSETS);
        setSpacing(1.0);

        for (int mx = 0; mx < shelfWidth; ++mx) {
            var module = new BlankModule(1);
            getChildren().add(module);
            _modules.put(mx, module);
        }
    }

    /**
     * Should only be invoked on the Application thread.
     * <p>
     * Places a Module into the Shelf at the indicated zero-based location, if possible.
     * There must be enough space from the given location to the end of the shelf.
     * e.g., a two-space module must be located no further to the right of location 5, of a shelf of width 7.
     * Further, all potential spaces to be populated must contain only BlankModule objects.
     * You cannot replace an existing non-blank module with the new module.
     * A BlankModule may be placed, but it will have no particular functional effect since it will replace
     * an existing BlankModule.
     * @param location zero-based location for the module
     * @param module the module to be placed
     */
    public boolean placeModule(
        final int location,
        final Module module
    ) {
        //  Ensure the targeted space(s) is/are blank...
        int lastLocation = location + module.getModuleWidth() - 1;
        for (int loc = location; loc <= lastLocation; ++loc) {
            if (!(_modules.get(loc) instanceof BlankModule)) {
                return false;
            }
        }

        //  Remove the existing module panes from the hbox, then add them back.
        getChildren().clear();

        //  Remove the relevant blank modules, and add the new one
        for (int loc = location; loc <= lastLocation; ++loc) {
            _modules.remove(loc);
        }
        _modules.put(location, module);

        //  Rebuild the child list
        getChildren().addAll(_modules.values());
        return true;
    }

    //  Only to be run on the Application thread
    public void repaint() {
        _modules.values().forEach(Module::repaint);
    }
}
