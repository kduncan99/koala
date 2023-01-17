/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.patches;

import com.kadware.koala.modules.Module;

public class DingSmearPatch extends Patch {

    public DingSmearPatch() {
        //  TODO Build and connect modules
    }

    @Override
    public final void close() {
        //  TODO disconnect modules
        _modules.forEach(Module::close);
    }
}
