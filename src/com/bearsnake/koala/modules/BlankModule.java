/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

public final class BlankModule extends Module {

    public BlankModule(
        final int ModuleWidth
    ) {
        super(ModuleWidth, "");
    }

    @Override
    public void advance() {}

    @Override
    public void close() {}

    @Override
    public void repaint() {}

    @Override
    public void reset() {}
}
