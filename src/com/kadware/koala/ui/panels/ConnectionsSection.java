/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.ui.panels.elements.connections.BlankConnectionPane;
import com.kadware.koala.ui.panels.elements.connections.ConnectionPane;
import javafx.scene.control.Label;

public class ConnectionsSection extends PanelSection {

    protected static final int OUTPUT_CAPTION_ROW_INDEX = 0;
    protected static final int OUTPUT_ROW_INDEX = 1;
    protected static final int INPUT_CAPTION_ROW_INDEX = 2;
    protected static final int INPUT_ROW_INDEX = 3;

    public ConnectionsSection(
        final PanelWidth panelWidth
    ) {
        super(panelWidth);

        var outputLabel = new Label("Outputs");
        outputLabel.setTextFill(Panel.PANEL_LEGEND_COLOR);
        add(outputLabel, 0, OUTPUT_CAPTION_ROW_INDEX, _horizontalCellCount, 1);

        var inputLabel = new Label("Inputs");
        inputLabel.setTextFill(Panel.PANEL_LEGEND_COLOR);
        add(inputLabel, 0, INPUT_CAPTION_ROW_INDEX, _horizontalCellCount, 1);

        for (int rx : new int[]{OUTPUT_ROW_INDEX, INPUT_ROW_INDEX}) {
            for (int cx = 0; cx < _horizontalCellCount; ++cx) {
                add(new BlankConnectionPane(), cx, rx, 1, 1);
            }
        }
    }

    void setInputConnection(
        final int index,
        final ConnectionPane connection
    ) {
        if (index < 0 || index >= _horizontalCellCount)
            throw new RuntimeException("input connection index out of range:" + index);
        //  Assumes only one input row
        add(connection, index, INPUT_ROW_INDEX);
    }

    void setOutputConnection(
        final int index,
        final ConnectionPane connection
    ) {
        if (index < 0 || index >= _horizontalCellCount)
            throw new RuntimeException("input connection index out of range:" + index);
        //  Assumes only one output row
        add(connection, _horizontalCellCount - index - 1, OUTPUT_ROW_INDEX);
    }

    //  Only for Application thread
    public void repaint() {
        for (var p : getChildren()) {
            if (p instanceof ConnectionPane cp) {
                cp.repaint();
            }
        }
    }
}
