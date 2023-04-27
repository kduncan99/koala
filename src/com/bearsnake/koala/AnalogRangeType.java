/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

public enum AnalogRangeType {
    BIPOLAR(new DoubleRange(-1.0, 1.0)),
    POSITIVE(new DoubleRange(0.0, 1.0));

    public final DoubleRange _value;

    AnalogRangeType(
        final DoubleRange value
    ) {
        _value = value;
    }
}
