/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020-2023 by Kurt Duncan - All Rights Reserved
 */

//  TODO
//      (INPR) Ability to load/save connections as part of a configuration
//      (INPR) Implement ability to apply a configuration, and to save one
//      Can we have modules with smaller control section and larger connection section?
//      Ability to add shelves to a rack via GUI
//      Ability to add modules to a rack/shelf via GUI
//      Ability to move shelves within the rack
//          implies redrawing the wires associated with all of the modules which move...
//              maybe we should just reposition all the wires after a module is moved.
//      Ability to move modules within a shelf, and across shelves
//          same wire concerns as above
//      Ability to remove (unconnected) modules from a shelf via GUI
//      Ability to remove (empty) shelves from a rack via GUI
//      Implement Para-modules
//          click on the modules to be included in the para module
//          choose which ports and controls are propagated to the para module panel
//          choose layout of the propagated ports and controls
//          store the configuration in a chosen directory
//      How do we implement polyphony?
//      StereoDBGraphPane see comments
//      Update LFO module - see notes in that module
//      Implement signal Modifier - everything (see notes)
//      Implement ability to morph settings for a particular module
//          (can we use config struct for this?)
//          (or can we implement MorphingModule base class and... make it work that way somehow?)
//      new Signal Delay module
//      new Sequencer module
//      new ClipPlayback module
//      Implement Scales (various temperaments, 1/4 tone, etc)
//      new MIDI-to-discrete module (where the output represents note)
//              implies new note-to-frequency module which uses a Temperament object for translations
//                  to convert note to frequency (discrete) output
//      new misc. logic modules for handling gates/triggers
//      new Envelope module (ADSR, no need for initial delay... but we could do DADSR just for kicks)
//      new MultiStage envelope module delay-attack-skew-level1-wait-skew-sustain-release
//      new dynamic ADSR, where various values are dependent upon an impulse level (e.g., key velocity)
//      new Signal Mixer module
//      HFO module (high-freq oscillator)

package com.bearsnake.koala;

import com.bearsnake.koala.modules.NoiseGeneratorModule;
import com.bearsnake.koala.modules.SimpleLFOModule;
import com.bearsnake.koala.modules.StereoOutputModule;
import com.bearsnake.koala.modules.VariableControlledAmplifierModule;
import com.bearsnake.koala.modules.VariableControlledPanModule;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    //  This class exists so that independently updating indicators get a chance to
    //  periodically update their display. Not many modules, entities, whatever will need this.
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
        var rackContent = (VBox)_rack.getChildren().get(0);
        var lfo = new SimpleLFOModule(SimpleLFOModule.DEFAULT_NAME);
        _rack.placeModule(0, 0, lfo);

        var noise1 = new NoiseGeneratorModule(NoiseGeneratorModule.DEFAULT_NAME);
        _rack.placeModule(0, 1, noise1);

        var noise2 = new NoiseGeneratorModule(NoiseGeneratorModule.DEFAULT_NAME);
        _rack.placeModule(0, 2, noise2);

        var vcAmp = new VariableControlledAmplifierModule(VariableControlledAmplifierModule.DEFAULT_NAME);
        _rack.placeModule(0, 3, vcAmp);

        var vcPan = new VariableControlledPanModule(VariableControlledPanModule.DEFAULT_NAME);
        _rack.placeModule(0, 5, vcPan);

        var audio = new StereoOutputModule(StereoOutputModule.DEFAULT_NAME);
        _rack.placeModule(0, 18, audio);

        var lfoOut = lfo.getOutputPort(SimpleLFOModule.SIGNAL_OUTPUT_PORT_ID);

        var noise1Out = noise1.getOutputPort(NoiseGeneratorModule.SIGNAL_OUTPUT_PORT_ID);
        var noise2Out = noise2.getOutputPort(NoiseGeneratorModule.SIGNAL_OUTPUT_PORT_ID);

        var vcAmpCtlIn = vcAmp.getInputPort(VariableControlledAmplifierModule.CONTROL_INPUT_PORT_ID);
        var vcAmpSigIn = vcAmp.getInputPort(VariableControlledAmplifierModule.SIGNAL_INPUT_PORT_ID);
        var vcAmpSigOut = vcAmp.getOutputPort(VariableControlledAmplifierModule.SIGNAL_OUTPUT_PORT_ID);

        var vcPanCtlIn = vcPan.getInputPort(VariableControlledPanModule.CONTROL_INPUT_PORT_ID);
        var vcPanSigIn = vcPan.getInputPort(VariableControlledPanModule.SIGNAL_INPUT_PORT_ID);
        var vcPanLeftOut = vcPan.getOutputPort(VariableControlledPanModule.LEFT_OUTPUT_PORT_ID);
        var vcPanRightOut = vcPan.getOutputPort(VariableControlledPanModule.RIGHT_OUTPUT_PORT_ID);

        var audioLeftIn = audio.getInputPort(StereoOutputModule.LEFT_INPUT_PORT_ID);
        var audioRightIn = audio.getInputPort(StereoOutputModule.RIGHT_INPUT_PORT_ID);

        Platform.runLater(()->{
            _rack.connectPorts(lfoOut, vcPanCtlIn);
            _rack.connectPorts(noise1Out, vcPanSigIn);
            _rack.connectPorts(vcPanLeftOut, audioLeftIn);
            _rack.connectPorts(vcPanRightOut, audioRightIn);
        });
         //  TODO end temporary
    }

    @Override
    public void init() throws Exception {
        super.init();
        _paintTimer = new Timer();
        _paintTimer.scheduleAtFixedRate(new PaintTask(), 0, PAINT_PERIOD_MS);
    }

    @Override
    public void stop() throws Exception {
        _paintTimer.cancel();
        _rack.close();
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
