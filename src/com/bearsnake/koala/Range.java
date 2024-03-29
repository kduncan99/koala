/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

public class Range<T> {

    private final T _lowValue;
    private final T _highValue;

    public Range(
        final T lowValue,
        final T highValue
    ) {
        _lowValue = lowValue;
        _highValue = highValue;
    }

    public T getLowValue() { return _lowValue; }
    public T getHighValue() { return _highValue; }

    @Override
    public String toString() {
        return String.format("{%s:%s}", String.valueOf(_lowValue), String.valueOf(_highValue));
    }
}
