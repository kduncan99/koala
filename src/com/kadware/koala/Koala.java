/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020-2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala;

import com.kadware.koala.modules.ModuleManager;
import com.kadware.koala.ui.Rack;
import com.kadware.koala.waves.IWave;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Koala extends Application {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static final double SAMPLE_RATE = 44100.0f;
    public static final int SAMPLE_SIZE_IN_BITS = 16;

    public static final DoubleRange BIPOLAR_RANGE = new DoubleRange(-1.0, 1.0);
    public static final DoubleRange POSITIVE_RANGE = new DoubleRange(0.0, 1.0);

    //  Note frequencies for C4 up through B4 - divide or multiply for other octaves
//    private static final float NF_C = 261.63f;
//    private static final float NF_CS = 277.18f;
//    private static final float NF_D = 293.66f;
//    private static final float NF_DS = 311.13f;
//    private static final float NF_E = 329.63f;
//    private static final float NF_F = 349.23f;
//    private static final float NF_FS = 349.23f;
//    private static final float NF_G = 392.00f;
//    private static final float NF_GS = 415.30f;
//    private static final float NF_A = 440f;
//    private static final float NF_AS = 466.16f;
//    private static final float NF_B = 493.88f;

    private static final long PAINT_PERIOD_MS = frequencyToMilliseconds(100);

    private Rack _rack;
    private Timer _timer;

    private class PaintTask extends TimerTask {

        private final PaintRunnable _runnable = new PaintRunnable();

        @Override
        public void run() {
            Platform.runLater(_runnable);
        }
    }

    private class PaintRunnable implements Runnable {

        @Override
        public void run() {
            if (_rack != null)
                _rack.repaint();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        //  TODO later we'll do load/save, have a starting dialog, all that crap
        var root = new Group();
        var scroller = new ScrollPane(root);
        var scene = new Scene(scroller);

        _rack = Rack.createEmptyRack(1, 11);
        root.getChildren().add(_rack);

        stage.setTitle("Koala - v1.0");//   TODO later pull version from somewhere useful
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init(
    ) throws Exception {
        super.init();
        _timer = new Timer();
        _timer.scheduleAtFixedRate(new PaintTask(), 0, PAINT_PERIOD_MS);
        ModuleManager.start();
    }

    @Override
    public void stop(
    ) throws Exception {
        _timer.cancel();
        ModuleManager.clear();
        ModuleManager.stop();

        super.stop();
    }

    public static void drawWave(
        final IWave wave,
        final Canvas canvas
        ) {
        var gc = canvas.getGraphicsContext2D();

        gc.beginPath();
        for (int x = 0; x < canvas.getWidth(); ++x) {
            double position = x / canvas.getWidth();
            var raw = wave.getValue(position, 0.5);
            var y = (int)((1.0 - ((raw + 1.0) / 2.0)) * canvas.getHeight());

            if (x == 0)
                gc.moveTo(x, y);
            else
                gc.lineTo(x, y);
        }
        gc.stroke();
        gc.closePath();
    }

    public static int getBounded(
        final int lowLimit,
        final int value,
        final int highLimit
    ) {
        return Math.min(Math.max(lowLimit, value), highLimit);
    }

    public static long getBounded(
        final long lowLimit,
        final long value,
        final long highLimit
    ) {
        return Math.min(Math.max(lowLimit, value), highLimit);
    }

    public static float getBounded(
        final float lowLimit,
        final float value,
        final float highLimit
    ) {
        return Math.min(Math.max(lowLimit, value), highLimit);
    }

    public static double getBounded(
        final double lowLimit,
        final double value,
        final double highLimit
    ) {
        return Math.min(Math.max(lowLimit, value), highLimit);
    }

    public static double getNextRandomCV() {
        return RANDOM.nextDouble() * 2.0 - 1.0;
    }

    /**
     * Converts a dbScalar to a flat scalar value which can be used for multiplying against a sample
     * to effect an increase or decrease in volume level.  The relevant equation is:
     *      scalar = 10 ^ (db / 20)
     * where we are expecting db to be positive for amplification, or negative for attenuation...
     * and db is derived from the dbScalar input linearly such that:
     *      dbScalar == -1.0 -> db == -96
     *      dbScalar ==  0.0 -> db == 0
     *      dbScalar == +1.0 -> db == 96
     * This is a voltage, NOT a power equation.
     * Generally, if db==3 we expect the scalar to be around 1.4, while if db==-3, we expect it to be around 0.7.
     * @param dbScalar value ranging from -1.0 (infinite attenuation) to 0.0 (no attenuation).
     * @return a multiplicative scalar which, when applied to some sample, will produce the desired attenuation.
     */
    public static double dbScalarToMultiplier(
        final double dbScalar
    ) {
        var db = dbScalar * 96.0;
        return Math.pow(10, db);
    }

    /**
     * Converts a frequency specified in hertz, to the time elapsed per cycle expressed in milliseconds
     * @param frequency input value
     * @return result
     */
    public static long frequencyToMilliseconds(
        final long frequency
    ) {
        return (long)(1000.0 / (double)frequency);
    }
}
