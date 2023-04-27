/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.koala.messages.controls;

import com.bearsnake.koala.AnalogRangeType;
import com.bearsnake.koala.messages.ControlMessage;

/**
 * Used by RangeTypeSelectorButtonControl to notify listeners that a range has been selected
 */
public class RangeTypeSelectorControlMessage extends ControlMessage {

    private final AnalogRangeType _range;

    public RangeTypeSelectorControlMessage(
        final AnalogRangeType range
    ) {
        _range = range;
    }

    public AnalogRangeType getRangeTypeValue() { return _range; }
}
