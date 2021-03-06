/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

/**
 * Describes an input or output port with discrete values, which may be interpreted
 * in any way by the input and output ports which implement them.
 * There are no arbitrary restrictions on the range of values, nor the individual values.
 */
public abstract class DiscretePort extends Port {

    public DiscretePort(
        final String name,
        final String abbreviation
    ) {
        super(name, abbreviation);
    }

    @Override
    public abstract void reset();
}
