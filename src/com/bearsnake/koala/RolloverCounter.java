/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

public class RolloverCounter {

    private final IntegerRange _range;
    private int _value;

    public RolloverCounter(
        final IntegerRange range
    ) {
        _range = range;
        _value = _range.getLowValue();
    }

    public void increment() {
        _value++;
        if (_value >= _range.getHighValue())
            _value = _range.getLowValue();
    }

    public int getValue() { return _value; }

    public void setValue(
        final int value
    ) {
        _value = _range.clipValue(value);
    }
}
