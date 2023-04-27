/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules.elements.ports;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * A graphic (and active) representation of an input connector.
 */
public class InputJack extends Jack {

    private enum State {
        NO_SIGNAL(Color.BLACK, 0),
        SIGNAL(Color.LIGHTGREEN, 100),
        OVERLOAD(Color.RED, 250);

        public final Color _color;
        public final long _delay;    //  in ms

        State(
            final Color color,
            final long delay
        ) {
            _color = color;
            _delay = delay;
        }
    }

    private class Task extends TimerTask {

        @Override
        public void run() {
            setState(State.NO_SIGNAL);
        }
    }

    private static final Timer TIMER = new Timer(true);

    private State _currentState = State.NO_SIGNAL;
    private Task _task = null;

    public InputJack(
        final double radius,
        final Color ringColor
    ) {
        super(radius, ringColor);

        var label = new Label("->");
        label.setTextFill(ringColor);
        getChildren().add(label);
        getChildren().add(_circle);
    }

    private void killTask() {
        if (_task != null) {
            _task.cancel();
            _task = null;
        }
    }

    private void setState(
        final State newState
    ) {
        killTask();
        _currentState = newState;
        _circle.setFill(newState._color);
        if (newState != State.NO_SIGNAL) {
            _task = new Task();
            TIMER.schedule(_task, newState._delay);
        }
    }

    public synchronized void setSignalDetected() {
        if (_currentState == State.NO_SIGNAL) {
            setState(State.SIGNAL);
        }
    }

    public synchronized void setOverload() {
        if (_currentState != State.OVERLOAD) {
            setState(State.OVERLOAD);
        }
    }
}
