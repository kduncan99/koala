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
    public Configuration getConfiguration() {
        return new Configuration(getIdentifier(), getName());
    }

    @Override
    public void setConfiguration(final Configuration configuration) {
        setIdentifier(configuration._identifier);
    }
}
