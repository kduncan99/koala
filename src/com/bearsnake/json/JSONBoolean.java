/*
 * Simple JSON library
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.json;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class JSONBoolean extends JSONValue {

    public final boolean _value;

    public JSONBoolean(
        final boolean value
    ) {
        _value = value;
    }

    public static JSONBoolean deserialize(
        final Parser parser
    ) {
        if (parser.parseToken("true")) {
            return new JSONBoolean(true);
        } else if (parser.parseToken("false")) {
            return new JSONBoolean(false);
        } else {
            return null;
        }
    }

    @Override
    public void serialize(
        final OutputStream output
    ) throws IOException {
        var text = String.valueOf(_value);
        output.write(text.getBytes(StandardCharsets.UTF_8));
    }
}
