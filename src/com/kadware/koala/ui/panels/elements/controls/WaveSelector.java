/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.ui.panels.elements.controls;

import com.kadware.koala.Koala;
import com.kadware.koala.PixelDimensions;
import com.kadware.koala.ui.components.buttons.Button;
import com.kadware.koala.ui.components.buttons.SelectorButton;
import com.kadware.koala.waves.IWave;
import com.kadware.koala.waves.WaveManager;
import com.kadware.koala.waves.WaveType;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

import java.util.Arrays;

/**
 * A button control is a button of some sort, above a legend.
 * Note that we are talking about a Koala button, not a JavaFX button.
 */
public class WaveSelector extends ButtonControl {

    private static final PixelDimensions GRAPHIC_DIMENSIONS =
        new PixelDimensions(BUTTON_DIMENSIONS.getWidth() - 8, BUTTON_DIMENSIONS.getHeight() - 8);

    private static class WaveGraphic {

        public final WaveType _waveType;
        public final IWave _wave;
        public final Pane _wavePane;

        public WaveGraphic(
            final WaveType waveType
        ) {
            _waveType = waveType;
            _wave = WaveManager.createWave(waveType);
            var canvas = new Canvas(GRAPHIC_DIMENSIONS.getWidth(), GRAPHIC_DIMENSIONS.getHeight());
            Koala.drawWave(_wave, canvas);
            _wavePane = new Pane(canvas);
            _wavePane.setPrefWidth(GRAPHIC_DIMENSIONS.getWidth());
            _wavePane.setPrefHeight(GRAPHIC_DIMENSIONS.getHeight());
        }
    }

    private static final WaveGraphic[] _waveGraphics = new WaveGraphic[] {
        new WaveGraphic(WaveType.SINE),
        new WaveGraphic(WaveType.RAMP),
        new WaveGraphic(WaveType.TRIANGLE),
        new WaveGraphic(WaveType.SAWTOOTH),
        new WaveGraphic(WaveType.SQUARE),
    };

    public WaveSelector() {
        super(createButton(), "Wave");
    }

    private static Button createButton() {
        var panes = Arrays.stream(_waveGraphics).map(waveGraphic -> waveGraphic._wavePane).toArray(Pane[]::new);
        return new SelectorButton(BUTTON_DIMENSIONS, panes);
    }
}
