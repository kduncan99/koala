/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

/**
 * This is just so we have a convenient way to tell if a module is an output module.
 * If it is, then it subclasses this class.
 */
public abstract class OutputModule extends Module {

    public OutputModule(
        final int ModuleWidth,
        final String moduleName
    ) {
        super(ModuleWidth, moduleName);
    }
}
