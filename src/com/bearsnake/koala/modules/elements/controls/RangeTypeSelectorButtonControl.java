/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.AnalogRangeType;
import com.bearsnake.koala.components.ui.buttons.Button;
import com.bearsnake.koala.components.ui.buttons.LabelSelectorButton;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.messages.controls.RangeTypeSelectorControlMessage;
import com.bearsnake.koala.messages.components.SelectorButtonComponentMessage;
import javafx.scene.paint.Color;

/**
 * This control is for selection an output range of either -1.0 to 1.0, or 0.0 to 1.0.
 */
//TODO obsolete?
public class RangeTypeSelectorButtonControl extends ButtonControl {

    private static final String[] LABELS = { "+/-", "+" };
    private static final Color[] COLORS = { Color.LIGHTGRAY, Color.LIGHTGRAY };
    private static final AnalogRangeType[] RANGE_TYPE = { AnalogRangeType.BIPOLAR, AnalogRangeType.POSITIVE };

    public RangeTypeSelectorButtonControl() {
        super(CELL_DIMENSIONS, createButton(), "range");
        getButton().registerListener(this);
    }

    private static Button createButton() {
        return new LabelSelectorButton(BUTTON_DIMENSIONS, LABELS, COLORS);
    }

    @Override
    public void notify(
        final int identifier,
        final Message message
    ) {
        if (message instanceof SelectorButtonComponentMessage msg) {
            var enumIndex = msg.getNewSelectorValue();
            notifyListeners(new RangeTypeSelectorControlMessage(RANGE_TYPE[enumIndex]));
        }
    }
}
