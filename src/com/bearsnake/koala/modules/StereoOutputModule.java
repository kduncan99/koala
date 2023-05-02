/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020,2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.modules;

import com.bearsnake.koala.DoubleRange;
import com.bearsnake.koala.Koala;
import com.bearsnake.koala.components.signals.DBFSAverager;
import com.bearsnake.koala.components.signals.Oscillator;
import com.bearsnake.koala.messages.IListener;
import com.bearsnake.koala.messages.Message;
import com.bearsnake.koala.messages.controls.TestToneSelectorControlMessage;
import com.bearsnake.koala.modules.elements.controls.UIControl;
import com.bearsnake.koala.modules.elements.ports.AnalogDestinationPort;
import com.bearsnake.koala.modules.elements.controls.LabeledPotentiometerControl;
import com.bearsnake.koala.modules.elements.controls.StatefulButtonControl;
import com.bearsnake.koala.modules.elements.controls.StereoDBFSIndicator;
import com.bearsnake.koala.modules.elements.controls.TestToneSelectorButtonControl;
import com.bearsnake.koala.waves.WaveType;
import javafx.scene.paint.Color;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import static com.bearsnake.koala.Koala.SAMPLE_SIZE_IN_BITS;

public class StereoOutputModule extends OutputModule implements IListener {

    public static class StereoOutputConfiguration extends Module.Configuration {

        public final UIControl.State _dimControlState;
        public final UIControl.State _gainControlState;
        public final UIControl.State _muteControlState;
        public final UIControl.State _testToneSelectorState;

        public StereoOutputConfiguration(
            final int identifier,
            final String name,
            final UIControl.State dimControlState,
            final UIControl.State gainControlState,
            final UIControl.State muteControlState,
            final UIControl.State testToneControlState
        ) {
            super(identifier, name);
            _dimControlState = dimControlState;
            _gainControlState = gainControlState;
            _muteControlState = muteControlState;
            _testToneSelectorState = testToneControlState;
        }
    }

    public static final String DEFAULT_NAME = "Output";
    public static final int LEFT_INPUT_PORT_ID = 0;
    public static final int RIGHT_INPUT_PORT_ID = 1;

    private static final int CHANNELS = 2;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = true;
    private static final AudioFormat AUDIO_FORMAT =
        new AudioFormat((float) Koala.SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    private static final double SAMPLE_MAGNITUDE = (1 << (SAMPLE_SIZE_IN_BITS - 1)) - 1;

    //  ports
    private final AnalogDestinationPort _inputPortLeft;
    private final AnalogDestinationPort _inputPortRight;

    //  controls and indicators
    private final StereoDBFSIndicator _dbfsIndicator;
    private final StatefulButtonControl _dimControl;
    private final LabeledPotentiometerControl _gainControl;
    private final StatefulButtonControl _muteControl;
    private final TestToneSelectorButtonControl _testToneSelectorControl;

    //  audio stuff
    private final byte[] _buffer = new byte[4];
    private final DBFSAverager _dbfsAveragerLeft;
    private final DBFSAverager _dbfsAveragerRight;
    private SourceDataLine _sourceDataLine;

    //  other stuff
    private final Oscillator _testTone = new Oscillator();
    private boolean _testToneEnabled = false;

    public StereoOutputModule(
        final String moduleName
    ) {
        super(2, moduleName);

        _testTone.setWaveForm(WaveType.SINE);
        _dbfsAveragerLeft = new DBFSAverager();
        _dbfsAveragerRight = new DBFSAverager();

        //  controls and indicators
        var section = getControlsSection();
        _dbfsIndicator = new StereoDBFSIndicator("audio");
        _dimControl = new StatefulButtonControl("dim", Color.BLACK, Color.YELLOW, Color.DARKGRAY);
        _gainControl = new LabeledPotentiometerControl("db gain",
                                                       new DoubleRange(-96.0, 6.0),
                                                       Color.LIGHTBLUE,
                                                       Color.BLACK,
                                                       "%2.1f",
                                                       0.0);
        _muteControl = new StatefulButtonControl("off", Color.BLACK, Color.RED, Color.DARKGRAY);
        _testToneSelectorControl = new TestToneSelectorButtonControl();

        _testToneSelectorControl.registerListener(this);

        //  TODO balance meter (1x2)
        //  TODO bias control
        //  TODO blend control
        //  TODO mono button
        section.setControl(0, 5, _testToneSelectorControl);
        section.setControl(1, 0, _dbfsIndicator);
        section.setControl(1, 3, _gainControl);
        section.setControl(1, 4, _dimControl);
        section.setControl(1, 5, _muteControl);

        //  ports
        _inputPortLeft = new AnalogDestinationPort(moduleName, "Left Input", "left");
        _inputPortRight = new AnalogDestinationPort(moduleName, "Right Input", "right");

        _ports.put(LEFT_INPUT_PORT_ID, _inputPortLeft);
        _ports.put(RIGHT_INPUT_PORT_ID, _inputPortRight);

        getPortsSection().setConnection(0, 0, _inputPortLeft);
        getPortsSection().setConnection(1, 0, _inputPortRight);

        //  finish setup
        reset();
    }

    @Override
    public synchronized void advance() {
        super.advance();
        _testTone.advance();

        var leftCombined = 0.0;
        var rightCombined = 0.0;
        if (!_muteControl.getButtonValue()) {
            //  get input signal and optionally mix in the test tone
            leftCombined = _inputPortLeft.getSignalValue();
            rightCombined = _inputPortRight.getSignalValue();
            if (_testToneEnabled) {
                leftCombined += _testTone.getValue() / 2.0;
                rightCombined += _testTone.getValue() / 2.0;
            }

            var scalar = Koala.dbToScalar(_gainControl.getScaledValue());
            leftCombined *= scalar;
            rightCombined *= scalar;
            if (_dimControl.getButtonValue()) {
                leftCombined /= 2;
                rightCombined /= 2;
            }
        }

        //  Send unscaled values to the averagers for eventual display
        _dbfsAveragerLeft.inject(leftCombined);
        _dbfsAveragerRight.inject(rightCombined);

        //  convert scaled values to system values (i.e., -1.0 <= n <= 1.0 --> -32767 <= n <= 32767
        int leftSystemScaled = (int)(leftCombined * SAMPLE_MAGNITUDE);
        int rightSystemScaled = (int)(rightCombined * SAMPLE_MAGNITUDE);
        _buffer[0] = (byte) (leftSystemScaled >> 8);
        _buffer[1] = (byte) leftSystemScaled;
        _buffer[2] = (byte) (rightSystemScaled >> 8);
        _buffer[3] = (byte) rightSystemScaled;
        _sourceDataLine.write(_buffer, 0, 4);
    }

    @Override
    public void close() {
        _sourceDataLine.stop();
        _sourceDataLine.flush();
        _sourceDataLine.close();
        _sourceDataLine = null;
    }

    @Override
    public Configuration getConfiguration() {
        return new StereoOutputConfiguration(
            getIdentifier(),
            getName(),
            _dimControl.getState(),
            _gainControl.getState(),
            _muteControl.getState(),
            _testToneSelectorControl.getState()
        );
    }

    @Override
    public void repaint() {
        _dbfsIndicator.setValues(_dbfsAveragerLeft.getDBFSValue(),
                                 _dbfsAveragerRight.getDBFSValue());
    }

    @Override
    public void reset() {
        _testTone.reset();
        _dbfsAveragerLeft.reset();
        _dbfsAveragerRight.reset();

        if (_sourceDataLine != null) {
            close();
        }

        try {
            SourceDataLine sdl = AudioSystem.getSourceDataLine(AUDIO_FORMAT);
            sdl.open(AUDIO_FORMAT, 4096);
            sdl.start();
            _sourceDataLine = sdl;
        } catch (LineUnavailableException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void setConfiguration(
        final Configuration configuration
    ) {
        setIdentifier(configuration._identifier);
        setName(configuration._name);
        if (configuration instanceof StereoOutputConfiguration cfg) {
            _dimControl.setState(cfg._dimControlState);
            _gainControl.setState(cfg._gainControlState);
            _muteControl.setState(cfg._muteControlState);
            _testToneSelectorControl.setState(cfg._testToneSelectorState);
        }
    }

    @Override
    public void notify(
        final int identifier,
        final Message message
    ) {
        if (identifier == _testToneSelectorControl.getIdentifier()) {
            var msg = (TestToneSelectorControlMessage) message;
            if (msg.getFrequency() == 0) {
                _testToneEnabled = false;
            } else {
                _testToneEnabled = true;
                _testTone.setFrequency(msg.getFrequency());
            }
        }
    }
}
