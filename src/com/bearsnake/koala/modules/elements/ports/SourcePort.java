/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.modules.Module;

/**
 * Base class for all output ports.
 * All output ports have at least a connection point graphic and an out-going arrow graphic.
 * Remember - we ultimately derive from VBox, so we can use that.
 * -----------
 * |  [] ->  |
 * | caption |
 * -----------
 */
public abstract class SourcePort extends ActivePort {

    protected SourcePort(
        final Module module,
        final String name,
        final OutputJack outputJack,
        final String caption
    ) {
        super(module, name, outputJack, caption);
        getChildren().add(_jack);
        getChildren().add(_label);
    }

    protected OutputJack getOutputJack() { return (OutputJack) _jack; }
}
