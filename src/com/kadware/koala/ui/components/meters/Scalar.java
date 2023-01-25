/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.components.meters;

public abstract class Scalar {

    /**
     * (re)scales an input value bounded between 0.0 and 1.0, to an output value 0.0 to 1.0
     */
    public abstract double getScaledValue(final double original);
}
