/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.patches;

import com.kadware.koala.modules.Module;

import java.util.LinkedList;
import java.util.List;

/**
 * A patch is a variation of a meta-module which implements a particular patch
 * (tone, timbre, whatever).  There are pre-set patches which are defined entirely in code, and there is the
 * loadable patch, which is configured entirely in an external data file which can be loaded and stored.
 */
public abstract class Patch extends Module {

    protected final List<Module> _modules = new LinkedList<>();

    @Override
    public final void advance() {
        _modules.forEach(Module::advance);
    }

    @Override
    public final ModuleType getModuleType() {
        return ModuleType.Patch;
    }
}
