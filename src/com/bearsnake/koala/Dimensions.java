/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala;

public class Dimensions<T> {

    private final T _width;
    private final T _height;

    public Dimensions(
        final T width,
        final T height
    ) {
        _width = width;
        _height = height;
    }

    public T getHeight() { return _height; }
    public T getWidth() { return _width; }

    @Override
    public String toString() {
        return String.format("{w=%s h=%s}", _width, _height);
    }
}
