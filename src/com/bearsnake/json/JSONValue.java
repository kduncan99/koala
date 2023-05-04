/*
 * Simple JSON library
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.json;

import java.io.IOException;
import java.io.OutputStream;

public abstract class JSONValue {

    public static JSONValue deserialize(
        final String input
    ) throws IOException {
        return deserialize(new Parser(input));
    }

    public static JSONValue deserialize(
        final Parser parser
    ) throws IOException {
        parser.skipWhiteSpace();
        if (parser.atEnd()) {
            return null;
        }

        JSONValue value = JSONNull.deserialize(parser);
        if (value == null) {
            value = JSONBoolean.deserialize(parser);
        }
        if (value == null) {
            value = JSONString.deserialize(parser);
        }
        if (value == null) {
            value = JSONNumber.deserialize(parser);
        }
        if (value == null) {
            value = JSONArray.deserialize(parser);
        }
        if (value == null) {
            value = JSONObject.deserialize(parser);
        }

        return value;
    }

    public abstract void serialize(
        final OutputStream output
    ) throws IOException;
}
