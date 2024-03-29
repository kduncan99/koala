/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.modules.Module;

/**
 * Base class for all input ports.
 * All input ports have at least a connection point graphic and an in-going arrow graphic.
 * Remember - we ultimately derive from VBox, so we can use that.
 * -----------
 * |  -> []  |
 * | caption |
 * -----------
 */
public abstract class DestinationPort extends ActivePort {

    protected DestinationPort(
        final Module module,
        final String name,
        final InputJack inputJack,
        final String caption
    ) {
        super(module, name, inputJack, caption);
        getChildren().add(_jack);
        getChildren().add(_label);
    }

    protected InputJack getInputJack() { return (InputJack) _jack; }

    public abstract void sampleSignal();
}
