/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala;

public class Pair<T, U> {

    private final T _leftValue;
    private final U _rightValue;

    public Pair(
        final T leftValue,
        final U rightValue
    ) {
        _leftValue = leftValue;
        _rightValue = rightValue;
    }

    public T getLeftValue() { return _leftValue; }
    public U getRightValue() { return _rightValue; }
}
