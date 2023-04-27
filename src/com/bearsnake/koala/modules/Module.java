/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.modules.elements.ports.OutputPort;
import com.bearsnake.koala.modules.elements.ports.Port;
import com.bearsnake.koala.modules.elements.ports.InputPort;
import com.bearsnake.koala.modules.sections.PortsSection;
import com.bearsnake.koala.modules.sections.ControlsSection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Base class for a Module object
 */
public abstract class Module extends VBox {

    public static final Color BACKGROUND_COLOR = Color.rgb(200, 200, 200);
    private static final BackgroundFill BACKGROUND_FILL = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
    private static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    private static final int VERTICAL_CELLS_CONTROLS = 6;
    private static final int VERTICAL_CELLS_CONNECTIONS = 2;

    private static final Set<Module> _modules = new HashSet<>();

    private final ControlsSection _controlsSection;
    private final int _moduleWidthCells;
    protected final Map<Integer, Port> _ports = new HashMap<>();
    private final PortsSection _portsSection;

    /**
     * Base constructor for all modules
     * @param moduleWidthCells width of module expressed as a number of cells (or shelf spaces).
     * @param caption name of the module, displayed at the top of the panel.
     */
    protected Module(
        final int moduleWidthCells,
        final String caption
    ) {
        _moduleWidthCells = moduleWidthCells;

        var cap = new Label(caption);
        cap.setAlignment(Pos.CENTER);
        var controlDims = new CellDimensions(moduleWidthCells, VERTICAL_CELLS_CONTROLS);
        _controlsSection = new ControlsSection(controlDims);
        var connectionDims = new CellDimensions(moduleWidthCells, VERTICAL_CELLS_CONNECTIONS);
        _portsSection = new PortsSection(connectionDims);

        setBackground(BACKGROUND);
        setBorder(Koala.OUTSET_BORDER);

        getChildren().add(cap);
        getChildren().add(_controlsSection);
        getChildren().add(_portsSection);
    }

    /**
     * Retrieves an input or output port by its module-specific identifier
     */
    public final Port getPort(
        final int portId
    ) {
        return _ports.get(portId);
    }

    /**
     * Convenience method
     */
    public final InputPort getInputPort(
        final int portId
    ) {
        return (InputPort)getPort(portId);
    }

    /**
     * Convenience method
     */
    public final OutputPort getOutputPort(
        final int portId
    ) {
        return (OutputPort)getPort(portId);
    }

    protected final ControlsSection getControlsSection() { return _controlsSection; }
    public final int getModuleWidth() { return _moduleWidthCells; }
    protected final PortsSection getPortsSection() { return _portsSection; }

    /**
     * Invoked by the static advanceAll() method.
     * Subclasses should override this and do any updates necessary.
     * Subclasses should call back here as part of their own advance() handling.
     */
    protected void advance() {
        //  Resample all the inputs
        for (var conn : _portsSection.getChildren()) {
            if (conn instanceof InputPort ip) {
                ip.sampleSignal();
            }
        }
    }

    /**
     * Must be called {n} times per second, where n is the system frequency.
     * Each Module must make any necessary adjustments to implement its functionality.
     * Because of the very fine resolution required for audio, it is expected that this
     * will be invoked by the same code which feeds the audio output (i.e., the SourceDataLine).
     * Alternatively, it could be driven by code which is capturing output to a file rather than to
     * real-time playback, in which case it frequency of invocation is not important.
     */
    public static void advanceAll() {
        synchronized (Module.class) {
            for (var module : _modules) {
                module.advance();
            }
        }
    }

    /**
     * Clears our module container after detaching all ports
     */
    public static void clear() {
        synchronized (Module.class) {
            for (Module module : _modules) {
                module.detachAllConnections();
                module.close();
            }

            _modules.clear();
        }
    }

    /**
     * Implementors should shut themselves down, as they are going to be discarded
     */
    public abstract void close();

    /**
     * Detaches all connections for this module's ports
     */
    public synchronized void detachAllConnections() {
        _ports.values().forEach(Port::disconnectAll);
    }

    /**
     * All modules must be registered with the static class.
     * However, we don't want them registered until they are ready to begin handling calls to advance(),
     * so we leave it up to each individual subclass to invoke this method when this requirement is met,
     * presumably from the constructor.
     */
    protected static void register(
        final Module module
    ) {
        synchronized (Module.class) {
            _modules.add(module);
        }
    }

    /**
     * Repaints the graphical content of the module.
     * Should be invoked only on the graphics thread.
     */
    public abstract void repaint();

    /**
     * General purpose reset
     */
    public abstract void reset();
}
