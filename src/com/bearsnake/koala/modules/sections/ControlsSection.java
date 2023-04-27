/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.sections;

import com.bearsnake.koala.CellDimensions;
import com.bearsnake.koala.modules.elements.controls.UIControl;
import com.bearsnake.koala.modules.elements.controls.BlankControl;
import javafx.scene.shape.Rectangle;

public class ControlsSection extends Section {

    public ControlsSection(
        final CellDimensions cellDimensions
    ) {
        super(cellDimensions);

        for (int vx = 0; vx < cellDimensions.getHeight(); ++vx) {
            for (int hx = 0; hx < cellDimensions.getWidth(); ++hx) {
                add(new BlankControl(), hx, vx);
            }
        }

        var clipDims = UIControl.determinePixelDimensions(cellDimensions);
        var clipRect = new Rectangle(clipDims.getWidth(), clipDims.getHeight());
        setClip(clipRect);
    }

    public void setControl(
        final int leftGridCell,
        final int topGridCell,
        final UIControl UIControl
    ) {
        add(UIControl,
            leftGridCell,
            topGridCell,
            UIControl.getCellDimensions().getWidth(),
            UIControl.getCellDimensions().getHeight());
    }
}
