/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.components.ui;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Models a simple circular LED which, when illuminated, will automatically extinguish itself
 * after some short period of time.
 */
public class FlashLEDDisplay extends InactiveComponent {

    //  TODO we might not need this class after all...
    //      or maybe we do, but call it a FlashAlert (and have a StaticAlert which does not reset without mouse-click)
    //      which we'll use on the DBFSMeter thing...
    private static final ScheduledExecutorService EXECUTOR = new ScheduledThreadPoolExecutor(1);
    private final Circle _circle;
    private final Color _color;
    private final int _delay;
    private ScheduledFuture<?> _task;

    public FlashLEDDisplay(
        final int radius,
        final Color color,
        final int delayInMillis
    ) {
        _color = color;
        _delay = delayInMillis;

        setPrefSize(2 * radius, 2 * radius);
        _circle = new Circle();
        _circle.setRadius(radius);
        _circle.setCenterX(radius);
        _circle.setCenterY(radius);
        _circle.setFill(color.brighter());

        getChildren().add(_circle);
    }

    private void extinguish() {
        _circle.setFill(Color.DARKGRAY);
    }

    public void flash() {
        if (_task != null) {
            _task.cancel(false);
            _task = null;
        }

        _circle.setFill(_color);
        _task = EXECUTOR.schedule(this::extinguish, 1, TimeUnit.SECONDS);
    }

    //  Only invoke from the Application thread
    public void paint() {
    }
}
