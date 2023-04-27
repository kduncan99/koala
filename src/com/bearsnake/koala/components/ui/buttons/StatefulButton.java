/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui.buttons;

import com.bearsnake.koala.PixelDimensions;
import com.bearsnake.koala.messages.components.StatefulButtonComponentMessage;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * An On/Off Button selects some particular state to on or off.
 * The label pane does not appreciably change;
 * however, the button state is indicated by the background color of the pane.
 */
public class StatefulButton extends LegendButton {

    private boolean _currentState;
    private final Background _disabledBackground;
    private final Background _enabledBackground;

    public StatefulButton(
        final PixelDimensions dimensions,
        final Pane legend,
        final Color enabledColor,
        final Color disabledColor
    ) {
        super(dimensions, legend);
        var disabledFill = new BackgroundFill(disabledColor, CornerRadii.EMPTY, Insets.EMPTY);
        var enabledFill = new BackgroundFill(enabledColor, CornerRadii.EMPTY, Insets.EMPTY);
        _disabledBackground = new Background(disabledFill);
        _enabledBackground = new Background(enabledFill);
        setState(false);
    }

    public StatefulButton(
        final PixelDimensions dimensions,
        final String legend,
        final Color legendColor,
        final Color enabledColor,
        final Color disabledColor
    ) {
        super(dimensions, legend, legendColor);
        var disabledFill = new BackgroundFill(disabledColor, CornerRadii.EMPTY, Insets.EMPTY);
        var enabledFill = new BackgroundFill(enabledColor, CornerRadii.EMPTY, Insets.EMPTY);
        _disabledBackground = new Background(disabledFill);
        _enabledBackground = new Background(enabledFill);
        setState(false);
    }

    public boolean getState() { return _currentState; }

    @Override
    protected void mouseClicked(MouseEvent event) {
        setState(!_currentState);
    }

    @Override
    protected void mousePressed(MouseEvent event) {
        super.setButtonPressed(true);
        notifyListeners(new StatefulButtonComponentMessage(_currentState));
    }

    @Override
    protected void mouseReleased(MouseEvent event) {
        super.setButtonPressed(false);
    }

    private void setState(
        final boolean value
    ) {
        _currentState = value;
        getLegendPane().setBackground(_currentState ? _enabledBackground : _disabledBackground);
    }
}
