/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.PixelDimensions;
import com.kadware.koala.messages.Message;
import com.kadware.koala.ui.components.buttons.LabelSelectorButton;
import com.kadware.koala.ui.panels.messages.TestToneMessage;
import com.kadware.koala.ui.components.buttons.Button;
import com.kadware.koala.ui.components.messages.SelectorButtonMessage;
import javafx.scene.paint.Color;

import java.util.Arrays;

/**
 * A button control is a button of some sort, above a legend.
 * Note that we are talking about a Koala button, not a JavaFX button.
 */
public class TestToneSelectorControl extends ButtonControl {

    private static final String[] LABELS = { "Off", "100", "440", "1k", "10k" };
    private static final PixelDimensions GRAPHIC_DIMENSIONS =
        new PixelDimensions(BUTTON_DIMENSIONS.getWidth() - 8, BUTTON_DIMENSIONS.getHeight() - 8);

    private static final int ID_SELECTOR_BUTTON = 0;

    private static final Tone[] _tones = new Tone[] {
        new Tone("Off", Color.DARKGRAY, 0.0),
        new Tone("100", Color.YELLOW, 100.0),
        new Tone("440", Color.YELLOW, 440.0),
        new Tone("1k", Color.YELLOW, 1000.0),
        new Tone("10k", Color.YELLOW, 10000.0),
    };

    private static class Tone {

        final String _legend;
        final Color _color;
        final double _frequency;

        public Tone(
            final String legend,
            final Color color,
            final double frequency
        ) {
            _legend = legend;
            _color = color;
            _frequency = frequency;
        }
    }

    public TestToneSelectorControl() {
        super(createButton(), "Tone");
    }

    private static Button createButton() {
        return new LabelSelectorButton(ID_SELECTOR_BUTTON, BUTTON_DIMENSIONS, getLabels(), getColors());
    }

    private static Color[] getColors() {
        return Arrays.stream(_tones).map(tone -> tone._color).toArray(Color[]::new);
    }

    private static String[] getLabels() {
        return Arrays.stream(_tones).map(tone -> tone._legend).toArray(String[]::new);
    }

    @Override
    public void notify(
        final Message message
    ) {
        //  The underlying button component has something for us... pass it along to our listeners
        if (message instanceof SelectorButtonMessage sbm)
            notifyListeners(new TestToneMessage(this,
                                                ID_SELECTOR_BUTTON,
                                                _tones[sbm.getNewSelectorValue()]._frequency));
    }
}
