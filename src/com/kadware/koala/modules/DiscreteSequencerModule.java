/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2020 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.DiscreteOutputPort;
import com.kadware.koala.ports.LogicInputPort;

public class DiscreteSequencerModule extends Module {

    public static final int TRIGGER_INPUT_PORT = 0;
    public static final int SIGNAL_OUTPUT_PORT = 1;

    private boolean _lastTriggerState = false;
    private int[] _values = null;
    private int _valueIndex = 0;

    DiscreteSequencerModule() {
        _inputPorts.put(TRIGGER_INPUT_PORT, new LogicInputPort("Logic Clock In", "CLK"));
        _outputPorts.put(SIGNAL_OUTPUT_PORT, new DiscreteOutputPort("Discrete Signal Out", "OUT"));
        reset();
    }

    @Override
    public void advance(
    ) {
        LogicInputPort inPort = (LogicInputPort) _inputPorts.get(TRIGGER_INPUT_PORT);
        boolean newTriggerState = inPort.getValue();
        if (_values != null && newTriggerState && !_lastTriggerState) {
            DiscreteOutputPort out = (DiscreteOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
            out.setCurrentValue(_values[_valueIndex]);

            ++_valueIndex;
            if (_valueIndex == _values.length) {
                _valueIndex = 0;
            }
        }

        _lastTriggerState = newTriggerState;
    }

    @Override
    public void close() {}

    @Override
    public String getModuleAbbreviation() {
        return "SEQ";
    }

    @Override
    public String getModuleClass() {
        return "Discrete Sequencer";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.DiscreteSequencer;
    }

    @Override
    public void reset() {
        _valueIndex = 0;
        DiscreteOutputPort out = (DiscreteOutputPort) _outputPorts.get(SIGNAL_OUTPUT_PORT);
        out.setCurrentValue(0);
    }

    public void setValues(
        final int[] values
    ) {
        if (values != null && values.length > 0) {
            _values = values;
        } else {
            _values = null;
        }

        reset();
    }
}
