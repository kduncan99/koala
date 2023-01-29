/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.audio.components;

/**
 * Base class for all Component objects
 * Such object are light-weight modules - intended to be used as building blocks for containing modules
 * (or for other components).
 * They *may* need to be advanced just like modules, but they do not have input or output ports.
 * They *may* respond to reset.
 * They *may* need to be closed before being dismissed.
 */
public abstract class Component {

    public abstract void advance();
    public abstract void close();
    public abstract void reset();
}
