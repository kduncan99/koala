/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.controls;

import com.bearsnake.koala.components.ui.buttons.Button;
import com.bearsnake.koala.components.ui.buttons.StatefulButton;
import com.bearsnake.koala.messages.Message;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Wraps a StatefulButton
 */
public class StatefulButtonControl extends ButtonControl {

    public class StatefulButtonState extends State {

        public final boolean _value;

        public StatefulButtonState(
            final boolean value
        ) {
            _value = value;
        }
    }

    public StatefulButtonControl(
        final Pane pane,
        final Color enabledColor,
        final Color disabledColor
    ) {
        super(CELL_DIMENSIONS, createButton(pane, enabledColor, disabledColor), "");
        getButton().registerListener(this);
    }

    public StatefulButtonControl(
        final String legend,
        final Color legendColor,
        final Color enabledColor,
        final Color disabledColor
    ) {
        super(CELL_DIMENSIONS, createButton(legend, legendColor, enabledColor, disabledColor), "");
        getButton().registerListener(this);
    }

    private static Button createButton(
        final Pane pane,
        final Color enabledColor,
        final Color disabledColor
    ) {
        return new StatefulButton(BUTTON_DIMENSIONS, pane, enabledColor, disabledColor);
    }

    private static Button createButton(
        final String legend,
        final Color legendColor,
        final Color enabledColor,
        final Color disabledColor
    ) {
        return new StatefulButton(BUTTON_DIMENSIONS, legend, legendColor, enabledColor, disabledColor);
    }

    public boolean getButtonValue() {
        return getStatefulButton().getValue();
    }

    @Override
    public State getState() {
        return new StatefulButtonState(getButtonValue());
    }

    public StatefulButton getStatefulButton() {
        return (StatefulButton) getButton();
    }

    @Override
    public void notify(
        final int identifier,
        final Message message
    ) {
        notifyListeners(message);
    }

    public void setButtonValue(
        final boolean value
    ) {
        getStatefulButton().setValue(value);
    }

    @Override
    public void setState(
        final State state
    ) {
        if (state instanceof StatefulButtonState st) {
            setButtonValue(st._value);
        }
    }
}
