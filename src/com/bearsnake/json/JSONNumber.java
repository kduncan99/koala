/*
 * Simple JSON library
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.json;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class JSONNumber extends JSONValue {

    public final String _value;

    public JSONNumber(
        final Long value
    ) {
        _value = String.format("%e", (double)value);
    }

    public JSONNumber(
        final Integer value
    ) {
        _value = String.format("%e", (double)value);
    }

    public JSONNumber(
        final Double value
    ) {
        _value = String.format("%e", value);
    }

    public JSONNumber(
        final Float value
    ) {
        _value = String.format("%e", value);
    }

    private JSONNumber(
        final String value
    ) {
        _value = value;
    }

    public int getInteger() {
        return Integer.parseInt(_value);
    }

    public long getLong() {
        return Long.parseLong(_value);
    }

    public double getDouble() {
        return Double.parseDouble(_value);
    }

    public double getFloat() {
        return Float.parseFloat(_value);
    }

    public static JSONNumber deserialize(
        final Parser parser
    ) {
        //  we store the string value as a string, but we verify it is in a useful format.
        parser.skipWhiteSpace();

        var sb = new StringBuilder();
        var neg = false;
        if (parser.parseToken("-")) {
            sb.append('-');
            neg = true;
        }

        char ch = parser.getNextChar();
        if (!Character.isDigit(ch)) {
            if (!neg) {
                return null;
            } else {
                throw new RuntimeException("Badly-formatted number");
            }
        }
        sb.append(ch);

        if (ch != '0') {
            ch = parser.parseDigit();
            while (ch != 0) {
                sb.append(ch);
                ch = parser.parseDigit();
            }
        }

        //  fraction
        if (parser.parseToken(".")) {
            sb.append('.');
            ch = parser.parseDigit();
            while (ch != 0) {
                sb.append(ch);
                ch = parser.parseDigit();
            }
        }

        //  exponent
        if (parser.parseToken("e") || parser.parseToken("E")) {
            sb.append('e');

            if (parser.parseToken("-")) {
                sb.append('-');
            } else if (parser.parseToken("+")) {
                sb.append('+');
            }

            ch = parser.parseDigit();
            while (ch != 0) {
                sb.append(ch);
                ch = parser.parseDigit();
            }
        }

        return new JSONNumber(sb.toString());
    }

    @Override
    public void serialize(
        final OutputStream output
    ) throws IOException {
        output.write(_value.getBytes(StandardCharsets.UTF_8));
    }
}
