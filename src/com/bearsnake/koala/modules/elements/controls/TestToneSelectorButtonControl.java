/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.components.ui.buttons.Button;
import com.bearsnake.koala.components.ui.buttons.LabelSelectorButton;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.messages.components.SelectorButtonComponentMessage;
import com.bearsnake.koala.messages.controls.TestToneSelectorControlMessage;
import javafx.scene.paint.Color;

/**
 * A button control is a button of some sort, above a legend.
 * Note that we are talking about a Koala button, not a JavaFX button.
 */
public class TestToneSelectorButtonControl extends ButtonControl {

    private static final String[] LABELS = { "off", "100", "440", "1k", "10k" };
    private static final Color[] COLORS = {
        Color.DARKGRAY,
        Color.CYAN,
        Color.CYAN,
        Color.CYAN,
        Color.CYAN,
    };
    private static final double[] FREQUENCIES = { 0.0, 100.0, 440.0, 1000.0, 10000.0 };

    public TestToneSelectorButtonControl() {
        super(CELL_DIMENSIONS, createButton(), "tone");
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
        //  The underlying button component has something for us... pass it along to our listeners
        if (message instanceof SelectorButtonComponentMessage msg) {
            var enumIndex = msg.getNewSelectorValue();
            notifyListeners(new TestToneSelectorControlMessage(FREQUENCIES[enumIndex]));
        }
    }
}
