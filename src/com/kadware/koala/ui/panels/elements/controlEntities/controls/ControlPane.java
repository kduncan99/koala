/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controlEntities.controls;

import com.kadware.koala.ui.panels.Panel;
import com.kadware.koala.ui.panels.elements.controlEntities.ControlEntityPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public abstract class ControlPane extends ControlEntityPane {

    protected ControlPane(
        final int horizontalCellCount,
        final int verticalCellCount,
        final String captionStr
    ) {
        super(horizontalCellCount, verticalCellCount);

        var caption = new Label(captionStr);
        caption.setAlignment(Pos.BASELINE_CENTER);
        caption.setTextFill(Panel.PANEL_LEGEND_COLOR);
        caption.setPrefSize(HORIZONTAL_PIXELS, VERTICAL_PIXELS);
    }
}