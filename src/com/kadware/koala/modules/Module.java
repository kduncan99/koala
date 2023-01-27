/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.exceptions.BadPortIndexException;
import com.kadware.koala.ports.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Module {

    final Map<Integer, IInputPort> _inputPorts = new HashMap<>();
    final Map<Integer, IOutputPort> _outputPorts = new HashMap<>();

    public abstract void advance();
    public abstract void close();

    public final void detachAllPorts() {
        for (IInputPort port : _inputPorts.values()) {
            port.disconnect();
        }
    }

    public final IInputPort getInputPort(
        final int portIndex
    ) {
        if (_inputPorts.containsKey(portIndex)) {
            return _inputPorts.get(portIndex);
        } else {
            throw new BadPortIndexException(portIndex);
        }
    }

    public abstract ModuleType getModuleType();

    public final IOutputPort getOutputPort(
        final int portIndex
    ) {
        if (_outputPorts.containsKey(portIndex)) {
            return _outputPorts.get(portIndex);
        } else {
            throw new BadPortIndexException(portIndex);
        }
    }

    public void reset() {
        for (IInputPort port : _inputPorts.values()) {
            port.reset();
        }
        for (IOutputPort port : _outputPorts.values()) {
            port.reset();
        }
    }
}
