/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ports;

/**
 * Base class for all input and output ports of all types.
 * Every module has at least one port.
 */
public abstract class Port {

    private String _abbreviation;
    private String _name;

    /**
     * Constructs a port
     * Generally this should only be invoked by the ModuleManager or by a meta-module.
     * @param name Full name of the port
     * @param abbreviation Abbreviation for the panel graphics - should be 3 to 5 characters
     */
    Port(
        final String name,
        final String abbreviation
    ) {
        _name = name;
        _abbreviation = abbreviation;
    }

    /**
     * Used by meta-modules to override the abbreviation assigned by the subordinate module
     */
    public void changeAbbreviation(
        final String value
    ) {
        _abbreviation = value;
    }

    /**
     * Used by meta-modules to override the name assigned by the subordinate module
     */
    public void changeName(
        final String value
    ) {
        _name = value;
    }

    /**
     * assigns an output port as the source for an input port
     * @param outputPort
     * @param inputPort
     */
    public static void connectPorts(
        final IOutputPort outputPort,
        final IInputPort inputPort
    ) {
        inputPort.connectTo(outputPort);
    }

    /**
     * Getter
     */
    public final String getAbbreviation() {
        return _abbreviation;
    }

    /**
     * Getter
     */
    public final String getName() {
        return _name;
    }

    /**
     * Resets the port.
     * Functionality is specific to the particular sub class (most probably don't do anything)
     */
    public abstract void reset();
}
