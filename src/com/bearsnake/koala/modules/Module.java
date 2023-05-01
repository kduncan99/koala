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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Base class for a Module object
 */
public abstract class Module extends VBox {

    /**
     * Contains the current configuration state of the module.
     * This includes all knob and switch settings - that is, the setting for any {foo}Control element.
     * The Configuration class does *not* include any interconnection state.
     */
    public static class Configuration {

        public final int _identifier;

        public Configuration(
            final int identifier
        ) {
            _identifier = identifier;
        }
    }

    public static final Color BACKGROUND_COLOR = Color.rgb(200, 200, 200);
    private static final BackgroundFill BACKGROUND_FILL = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
    private static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    private static final int VERTICAL_CELLS_CONTROLS = 6;
    private static final int VERTICAL_CELLS_CONNECTIONS = 2;
    private static final AtomicInteger _nextIdentifier = new AtomicInteger(1);
    private final ControlsSection _controlsSection;

    private int _identifier;
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
        _identifier = _nextIdentifier.getAndIncrement();
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

    public final int getIdentifier() {
        return _identifier;
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

    public abstract Configuration getConfiguration();
    protected final ControlsSection getControlsSection() { return _controlsSection; }
    public final int getModuleWidth() { return _moduleWidthCells; }
    protected final PortsSection getPortsSection() { return _portsSection; }

    /**
     * Invoked by the static advanceAll() method.
     * Subclasses should override this and do any updates necessary.
     * Subclasses should call back here as part of their own advance() handling.
     */
    public void advance() {
        //  Resample all the inputs
        for (var conn : _portsSection.getChildren()) {
            if (conn instanceof InputPort ip) {
                ip.sampleSignal();
            }
        }
    }

    /**
     * Implementors should shut themselves down, as they are going to be discarded
     */
    public abstract void close();

    /**
     * Repaints the graphical content of the module.
     * Should be invoked only on the graphics thread.
     */
    public abstract void repaint();

    /**
     * General purpose reset
     */
    public abstract void reset();

    /**
     * Sets the configuration of the subclass
     * @param configuration Configuration object, or module-specific subclass thereof.
     */
    public abstract void setConfiguration(final Configuration configuration);

    protected void setIdentifier(
        final int identifier
    ) {
        _identifier = identifier;
    }
}
