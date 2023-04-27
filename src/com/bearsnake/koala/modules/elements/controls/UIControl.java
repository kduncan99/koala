/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.Koala;
import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.messages.IListener;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.modules.elements.Element;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * Base class for everything in the ControlsSection.
 * Each entity is either an indicator, a control, or a combination of the two.
 * They are all laid out as some sort of visual entity above a descriptive label.
 * <p>
 * It is laid out as such:
 * <p>
 * ----------
 * |        |
 * | Entity |
 * |        |
 * ----------
 * | Legend |
 * ----------
 * <p>
 * Where the entity is some sort of graphical depiction of a control and/or an indicator.
 *       and legend is a text label describing the entity.
 * <p>
 * A control wraps a lower-level ui component with some semantic details which apply to a particular usage of that
 * component - e.g., the AnalogValueIndicator is a wrapper around a Meter component specifically for use in displaying
 * a continuously-ranging value from -1.0 to 1.0.
 * If it is necessary for the Control to be notified of inputs from the wrapped Component, it should implement
 * IListener and register with the Component to receive the input messages. If there are multiple components, it
 * may be necessary to check the identifier attribute of the message to know which Component sent the message.
 * <p>
 * A Control which handles input which must be passed up the stack should accept IListener object registrations,
 * and send messages to the collection of such objects, along with the Control identifier (which is not
 * the same as the Component identifier). A message from a Component can be passed through the Control to the
 * Control's listeners, so long as the sender identifier is updated appropriately.
 * <p>
 * Subclasses should be named {foo}Indicator if they act only to relay information to the user.
 * Subclasses should be named {foo}Control if they provide input from the user (regardless of whether they are *also* indicators).
 */
public abstract class UIControl extends Element {

    public static final int HORIZONTAL_PIXELS_PER_CELL = 40;
    public static final int VERTICAL_PIXELS_PER_CELL = 55;
    public static final int VERTICAL_PIXELS_FOR_LEGEND = 15;
    public static final Font LEGEND_FONT = new Font(10);

    private static final AtomicInteger _nextIdentifier = new AtomicInteger(1000);

    private final Collection<IListener> _listeners = new LinkedList<>();

    private final int _identifier;

    public UIControl(
        final CellDimensions cellDimensions
    ) {
        super(cellDimensions, determinePixelDimensions(cellDimensions));
        _identifier = _nextIdentifier.getAndIncrement();
    }

    public UIControl(
        final CellDimensions cellDimensions,
        final Pane control,
        final String legend
    ) {
        this(cellDimensions);
        getChildren().add(control);

        var label = new Label(legend);
        label.setPrefHeight(VERTICAL_PIXELS_FOR_LEGEND);
        label.setFont(LEGEND_FONT);
        label.setTextFill(Koala.LEGEND_COLOR);
        label.setPrefWidth(_pixelDimensions.getWidth());
        label.setAlignment(Pos.TOP_CENTER);
        getChildren().add(label);
    }

    public final int getEntityHeight() {
        return _pixelDimensions.getHeight() - VERTICAL_PIXELS_FOR_LEGEND;
    }

    public final int getIdentifier() { return _identifier; }

    //  Listener stuff ------------------
    public synchronized void notifyListeners(
        final Message message
    ) {
        _listeners.forEach(l -> l.notify(_identifier, message));
    }

    public synchronized void registerListener(
        final IListener listener
    ) {
        _listeners.add(listener);
    }

    public synchronized void unregisterListener(
        final IListener listener
    ) {
        _listeners.remove(listener);
    }

    //  Generally useful static stuff (potentially used in other packages)
    public static int determineEntityHeight(
        final int cellHeight
    ) {
        return determinePixelHeight(cellHeight) - VERTICAL_PIXELS_FOR_LEGEND;
    }

    public static int determinePixelHeight(
        final int cellHeight
    ) {
        return cellHeight * VERTICAL_PIXELS_PER_CELL;
    }

    public static int determinePixelWidth(
        final int cellWidth
    ) {
        return (cellWidth * HORIZONTAL_PIXELS_PER_CELL) + ((cellWidth - 1) * Element.INTER_CELL_SPACE);
    }

    public static PixelDimensions determinePixelDimensions(
        final CellDimensions cellDimensions
    ) {
        return new PixelDimensions(determinePixelWidth(cellDimensions.getWidth()),
                                   determinePixelHeight(cellDimensions.getHeight()));
    }
}
