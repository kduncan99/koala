/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.CellDimensions;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.messages.IListener;
import com.kadware.koala.messages.Message;
import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.Collection;
import java.util.LinkedList;

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
 */
public abstract class ControlPane extends VBox {

    public static final int HORIZONTAL_PIXELS_PER_CELL = 40;
    public static final int VERTICAL_PIXELS_PER_CONTROL = 40;
    public static final int VERTICAL_PIXELS_PER_LABEL = 15;
    public static final int VERTICAL_PIXELS_PER_CELL = VERTICAL_PIXELS_PER_CONTROL + VERTICAL_PIXELS_PER_LABEL;

    public static final Insets BACKGROUND_INSETS = new Insets(1);
    public static final BackgroundFill BACKGROUND_FILL = new BackgroundFill(Panel.PANEL_CELL_BACKGROUND_COLOR,
                                                                            CornerRadii.EMPTY,
                                                                            BACKGROUND_INSETS);
    public static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    public static final Font LEGEND_FONT = new Font(10);

    private final CellDimensions _cellDimensions;
    private final Collection<IListener> _listeners = new LinkedList<>();

    public ControlPane(
        final CellDimensions cellDimensions,
        final Pane entity,
        final String legend
    ) {
        _cellDimensions = cellDimensions;
        var pixelDimensions = toPixelDimensions(cellDimensions);

        setMinSize(pixelDimensions.getWidth(), pixelDimensions.getHeight());
        setPrefSize(pixelDimensions.getWidth(), pixelDimensions.getHeight());
        setMaxSize(pixelDimensions.getWidth(), pixelDimensions.getHeight());
        setBackground(BACKGROUND);

        if (entity != null) {
            getChildren().add(entity);
        }

        if (legend != null && !legend.isEmpty()) {
            var label = new Label(legend);
            label.setFont(LEGEND_FONT);
            label.setTextFill(Panel.PANEL_LEGEND_COLOR);
            label.setPrefWidth(pixelDimensions.getWidth());
            label.setAlignment(Pos.BOTTOM_CENTER);
            getChildren().add(label);
        }
    }

    public CellDimensions getCellDimensions() { return _cellDimensions; }

    //  To be invoked only on the Application thread.
    //  Subclasses override this *if* needed
    public void setValue(final double value){}

    public void notifyListeners(
        final Message message
    ) {
        _listeners.forEach(l -> l.notify(message));
    }

    public void registerListener(
        final IListener listener
    ) {
        _listeners.add(listener);
    }

    public void unregisterListener(
        final IListener listener
    ) {
        _listeners.remove(listener);
    }

    public static int getPixelHeight(
        final CellDimensions cellDimensions
    ) {
        return cellDimensions.getHeight() * VERTICAL_PIXELS_PER_CELL;
    }

    public static int getPixelWidth(
        final CellDimensions cellDimensions
    ) {
        return cellDimensions.getWidth() * HORIZONTAL_PIXELS_PER_CELL;
    }

    public static PixelDimensions toPixelDimensions(
        final CellDimensions cellDimensions
    ) {
        return new PixelDimensions(getPixelWidth(cellDimensions), getPixelHeight(cellDimensions));
    }
}
