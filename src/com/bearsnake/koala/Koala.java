/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020-2023 by Kurt Duncan - All Rights Reserved
 */

//  TODO
//      Draw connections
//      Implement ability to clear connections (in progress)
//          We will simply right-click and select Delete on the wire object's context menu
//      Implement ability to set connections
//          For connections - we need a Wire object, which will be a child of the rack
//              the wire objects will always be on top of all other graphic entities
//              The Rack object will be in charge of creating and deleting connections
//              * A Port will notice a drag enter, and notify the Rack.
//              * Some other entity will notice a drag completion, and notify the Rack.
//                  The Rack will do the rest.
//          Also, we can just tell the Rack to connect Module(m1).Port(p1) to Module(m2).Port(p2)
//          The Rack object will need to be able to determine the coordinates of the two ports involved.
//      StereoDBGraphPane see comments
//      Change ports:
//          Put port-type color spec in Port module as a static final
//          OutputPort - no overload or activity indication
//          InputPort
//              OnOff ports have on/off indication
//              Analog ports have activity and overload indications
//      Update LFO module - see notes in that module
//      Signal Modifier - everything
//      Signal Delay module
//      VCA module
//      Stereo PAN module
//      Envelope module (ADSR, no need for initial delay)
//      Signal Mixer module
//      HFO module (high-freq oscillator)

package com.bearsnake.koala;

import com.bearsnake.koala.modules.Module;
import com.bearsnake.koala.modules.NoiseGeneratorModule;
import com.bearsnake.koala.modules.SimpleLFOModule;
import com.bearsnake.koala.modules.StereoOutputModule;
import com.bearsnake.koala.modules.elements.ports.Port;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;

public class Koala extends Application {

    public static final Color BACKGROUND_COLOR = Color.rgb(223, 223, 223);
    public static final BackgroundFill BACKGROUND_FILL = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
    public static final Background BACKGROUND = new Background(BACKGROUND_FILL);

    public static final Color LEGEND_COLOR = Color.DARKBLUE;
    public static final Insets STANDARD_INSETS = new Insets(1.0);

    public static final int BORDER_WIDTH = 1;
    private static final BorderWidths SINGLE_BORDER_WIDTHS = new BorderWidths(BORDER_WIDTH);

    public static final BorderStroke BLANK_BORDER_STROKE =
        new BorderStroke(BACKGROUND_COLOR,
                         BACKGROUND_COLOR,
                         BACKGROUND_COLOR,
                         BACKGROUND_COLOR,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         CornerRadii.EMPTY,
                         SINGLE_BORDER_WIDTHS,
                         Insets.EMPTY);

    public static final Border BLANK_BORDER = new Border(BLANK_BORDER_STROKE);

    public static final BorderStroke INSET_BORDER_STROKE =
        new BorderStroke(Color.BLACK,
                         Color.WHITE,
                         Color.WHITE,
                         Color.BLACK,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         CornerRadii.EMPTY,
                         SINGLE_BORDER_WIDTHS,
                         Insets.EMPTY);

    public static final Border INSET_BORDER = new Border(INSET_BORDER_STROKE);

    public static final BorderStroke OUTSET_BORDER_STROKE =
        new BorderStroke(Color.WHITE,
                         Color.BLACK,
                         Color.BLACK,
                         Color.WHITE,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         BorderStrokeStyle.SOLID,
                         CornerRadii.EMPTY,
                         SINGLE_BORDER_WIDTHS,
                         Insets.EMPTY);
    public static final Border OUTSET_BORDER = new Border(OUTSET_BORDER_STROKE);

    public static final double SAMPLE_RATE = 44100.0f;
    public static final int SAMPLE_SIZE_IN_BITS = 16;

    public static final DoubleRange BIPOLAR_RANGE = new DoubleRange(-1.0, 1.0);
    public static final DoubleRange DBFS_RANGE = new DoubleRange(-30.0, 0.0);
    public static final DoubleRange POSITIVE_RANGE = new DoubleRange(0.0, 1.0);

    //TODO move this to some appropriate other place
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

    private boolean _inhibitPaint = false;
    private Rack _rack;
    private Timer _paintTimer;

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
            if (!_inhibitPaint && (_rack != null)) {
                _rack.repaint();
            }
        }
    }

    public static final Random RANDOM = new Random();

    @Override
    public void start(Stage stage) throws Exception {
        RANDOM.setSeed(System.currentTimeMillis());

        //  TODO later we'll do load/save, have a starting dialog, all that crap
        var root = new Group();
        var scroller = new ScrollPane(root);
        var scene = new Scene(scroller);

        _rack = new Rack(2, 20);
        root.getChildren().add(_rack);

        stage.setTitle("Koala - v1.0");//   TODO later pull version from somewhere useful
        stage.setScene(scene);
        stage.show();

        //  TODO temporary
        var s = (Shelf)_rack.getChildren().get(0);
        var lfo = new SimpleLFOModule();
        var noise1 = new NoiseGeneratorModule();
        var noise2 = new NoiseGeneratorModule();
        var out = new StereoOutputModule();
        s.placeModule(0, lfo);
        s.placeModule(1, noise1);
        s.placeModule(2, noise2);
        s.placeModule(18, out);

        var noise1Port = noise1.getOutputPort(NoiseGeneratorModule.SIGNAL_OUTPUT_PORT_ID);
        var noise2Port = noise2.getOutputPort(NoiseGeneratorModule.SIGNAL_OUTPUT_PORT_ID);
        var outLeftPort = out.getInputPort(StereoOutputModule.LEFT_INPUT_PORT_ID);
        var outRightPort = out.getInputPort(StereoOutputModule.RIGHT_INPUT_PORT_ID);
        Port.connect(noise1Port, outLeftPort);
        Port.connect(noise2Port, outRightPort);

        var qc1 = new QuadCurve();
        qc1.setStartX(noise1Port.getLayoutX());
         //  TODO end temporary
    }

    @Override
    public void init(
    ) throws Exception {
        super.init();
        _paintTimer = new Timer();
        _paintTimer.scheduleAtFixedRate(new PaintTask(), 0, PAINT_PERIOD_MS);
    }

    @Override
    public void stop(
    ) throws Exception {
        Module.clear();
        _paintTimer.cancel();
        super.stop();
    }

//    public static void drawWave(
//        final IWave wave,
//        final Canvas canvas
//        ) {
//        var gc = canvas.getGraphicsContext2D();
//
//        gc.beginPath();
//        for (int x = 0; x < canvas.getWidth(); ++x) {
//            double position = x / canvas.getWidth();
//            var raw = wave.getValue(position, 0.5);
//            var y = (int)((1.0 - ((raw + 1.0) / 2.0)) * canvas.getHeight());
//
//            if (x == 0)
//                gc.moveTo(x, y);
//            else
//                gc.lineTo(x, y);
//        }
//        gc.stroke();
//        gc.closePath();
//    }

    /**
     * Converts a dbFS value from -inf to 0, to a scalar value from 0.0 to 1.0.
     * Note that, for +dbFS values, we return an output value > 1.0.
     * dbFS        Output value
     *   0              1.0
     *  -3              0.9
     *  -6              0.8
     *  -9              0.7
     * -12              0.6
     * -15              0.5
     * -18              0.4
     * -21              0.3
     * -24              0.2
     * -27              0.1
     * -30 and less     0.0
     */
    //TODO do we need this?
//    public static double dbfsToMeterScale(
//        final double dbfs
//    ) {
//        return Math.max(1.0 - (-dbfs / 30), 0.0);
//    }

    /**
     * Converts a db scalar to an arithmetic scalar.
     * That is to say, +3 becomes 2.0, -3 becomes 0.5, etc.
     * @param db input db scalar
     * @return output arithmetic scalar
     */
    public static double dbToScalar(
        final double db
    ) {
        return Math.pow(2, db / 3.0);
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

    /**
     * Converts a multiplier which would be applied to a raw value to produce a scaled value,
     * to an appropriate db level.
     */
    public static double multiplierToDBScalar(
        final double multiplier
    ) {
        return Math.log10(multiplier) * 20.0 / 96.0;
    }

    /**
     * Determines the number of samples which are required to contain the given number of milliseconds of signal
     */
    public static int millisecondsToSamples(
        final int milliseconds
    ) {
        return (int)((milliseconds * SAMPLE_RATE) / 1000);
    }

    /**
     * Converts a scaled value between 0.0 and 1.0 to a dbfs value.
     * Note that we use the magnitude of the input value, in order to accommodate AC audio waves.
     * Input value      dbFS
     *     1.0            0
     *     0.5           -3
     *     0.25          -6
     *     0.125         -9
     *     0.0625       -12
     *     0.03125      -15
     *     0.015625     -18
     *     0.0078125    -21
     *     0.00390625   -24
     *     0.001953125  -27
     *     0.0         -inf
     */
    private static final double LOG_E_OF_TWO = Math.log(2);
    public static double waveAmplitudeToDBFS(
        final double scalar
    ) {
        if (scalar == 0.0) {
            return 0 - Double.MAX_VALUE;
        } else {
            return Math.log(Math.abs(scalar)) / LOG_E_OF_TWO * 3.0;
        }
    }
}
