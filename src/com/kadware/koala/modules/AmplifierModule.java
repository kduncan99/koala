package com.kadware.koala.modules;

import com.kadware.koala.Koala;
import com.kadware.koala.ports.InputPort;
import com.kadware.koala.ports.OutputPort;

public class AmplifierModule extends Module {

    public static final int INPUT_PORT = 0;
    public static final int CONTROL_PORT_1 = 1;
    public static final int CONTROL_PORT_2 = 2;
    public static final int CONTROL_PORT_3 = 3;
    public static final int CONTROL_PORT_4 = 4;
    public static final int OUTPUT_PORT = 0;

    private double _baseValue = 5.0;

    AmplifierModule() {
        _inputPorts.put(INPUT_PORT, new InputPort("Signal Input"));
        _inputPorts.put(CONTROL_PORT_1, new InputPort("Control 1"));
        _inputPorts.put(CONTROL_PORT_2, new InputPort("Control 2"));
        _inputPorts.put(CONTROL_PORT_3, new InputPort("Control 3"));
        _inputPorts.put(CONTROL_PORT_4, new InputPort("Control 4"));
        _outputPorts.put(OUTPUT_PORT, new OutputPort("Signal Output"));
    }

    @Override
    public void advance(
    ) {
        double multiplier = _baseValue;
        multiplier += _inputPorts.get(CONTROL_PORT_1).getValue();
        multiplier += _inputPorts.get(CONTROL_PORT_2).getValue();
        multiplier += _inputPorts.get(CONTROL_PORT_3).getValue();
        multiplier += _inputPorts.get(CONTROL_PORT_4).getValue();
        double value = _inputPorts.get(INPUT_PORT).getValue() * multiplier / Koala.MAX_PORT_MAGNITUDE;
        _outputPorts.get(OUTPUT_PORT).setCurrentValue(value);
    }

    @Override
    public void close() {}

    public double getBaseValue() {
        return _baseValue;
    }

    @Override
    public String getModuleClass() {
        return "Amplifier";
    }

    @Override
    public ModuleType getModuleType() {
        return ModuleType.Amplifier;
    }

    @Override
    public void reset() {}

    public void setBaseValue(
        final double value
    ) {
        _baseValue = value;
    }
}
