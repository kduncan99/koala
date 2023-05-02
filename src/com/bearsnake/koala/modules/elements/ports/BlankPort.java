/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import com.bearsnake.koala.Koala;

/**
 * Implements a blank/unused cell for PortsSection objects
 */
public class BlankPort extends Port {

    public BlankPort() {
        setBorder(Koala.BLANK_BORDER);
    }
}
