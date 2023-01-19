/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.connections;

import com.kadware.koala.ports.Port;
import com.kadware.koala.ui.panels.Panel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public abstract class ConnectionPane extends VBox {

    public static final int EDGE_PIXELS = 2 * ConnectionJack.VERTICAL_EDGE_PIXELS;
    public static final BackgroundFill BACKGROUND_FILL
        = new BackgroundFill(Panel.PANEL_CELL_BACKGROUND_COLOR, null, new Insets(1));
    public static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    public final Label _caption;
    public final ConnectionJack _jack;

    private ConnectionPane(
        final Label caption,
        final ConnectionJack jack
    ) {
        _caption = caption;
        _jack = jack;

        if (_caption != null) {
            _caption.setAlignment(Pos.BASELINE_CENTER);
            _caption.setTextFill(Panel.PANEL_LEGEND_COLOR);
            _caption.setPrefSize(EDGE_PIXELS, EDGE_PIXELS * 0.5);

            getChildren().addAll(_jack, _caption);
        }

        setPrefSize(EDGE_PIXELS, EDGE_PIXELS);
        setMaxSize(EDGE_PIXELS, EDGE_PIXELS);
        setMinSize(EDGE_PIXELS, EDGE_PIXELS);
        setBackground(BACKGROUND);
    }

    //  For a blank pane
    protected ConnectionPane() {
        this(null, (ConnectionJack) null);
    }

    protected ConnectionPane(
        final String captionStr,
        final Port port
    ) {
        this(new Label(captionStr),
             ConnectionJack.createConnectionJack(port));
    }

    //  Can only be invoked on the Application thread
    public void repaint() {
        if (_jack != null) {
            _jack.paint();
        }
    }
}
