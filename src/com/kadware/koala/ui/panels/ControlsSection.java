/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels;

import com.kadware.koala.ui.panels.elements.controlEntities.BlankControlEntity;
import com.kadware.koala.ui.panels.elements.controlEntities.ControlEntityPane;

public class ControlsSection extends PanelSection {

    private static final int VERTICAL_CELLS = 6;

    public ControlsSection(
        final PanelWidth panelWidth
    ) {
        super(panelWidth);

        for (int vx = 0; vx < VERTICAL_CELLS; ++vx) {
            for (int hx = 0; hx < _horizontalCellCount; ++hx) {
                add(new BlankControlEntity(), hx, vx);
            }
        }
    }

    public void setControlEntity(
        final int leftGridCell,
        final int topGridCell,
        final ControlEntityPane entity
    ) {
        add(entity, leftGridCell, topGridCell, entity.getHorizontalCellCount(), entity.getVerticalCellCount());
    }
}
