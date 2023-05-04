/*
 * Simple JSON library
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.json;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JSONObject extends JSONValue {

    public static class Pair {

        public final JSONString _string;
        public final JSONValue _value;

        public Pair(
            final JSONString string,
            final JSONValue value
        ) {
            _string = string;
            _value = value;
        }

        public static Pair deserialize(
            final Parser parser
        ) throws IOException {
            var js = JSONString.deserialize(parser);
            if (js == null) {
                return null;
            }

            if (!(js instanceof JSONString)) {
                throw new IOException("Invalid type for object key");
            }

            parser.skipWhiteSpace();
            if (!parser.parseToken(":")) {
                throw new IOException("Badly-formatted object value");
            }

            parser.skipWhiteSpace();
            var jv = JSONValue.deserialize(parser);
            if (jv == null) {
                throw new IOException("Incomplete object value");
            }

            return new Pair((JSONString) js, jv);
        }
    }

    public final Map<JSONString, JSONValue> _values = new HashMap<>();

    public JSONObject(
        final Map<JSONString, JSONValue> value
    ) {
        _values.putAll(value);
    }

    public static JSONObject deserialize(
        final Parser parser
    ) throws IOException {
        if (!parser.parseToken("[")) {
            return null;
        }

        var map = new HashMap<JSONString, JSONValue>();
        boolean terminatorOK = true;
        boolean pairOK = true;
        boolean separatorOK = false;
        boolean terminatorFound = false;
        while (!parser.atEnd()) {
            parser.skipWhiteSpace();
            if (separatorOK) {
                if (parser.parseToken(",")) {
                    separatorOK = false;
                    terminatorOK = false;
                    pairOK = true;
                    continue;
                }
            }

            if (terminatorOK) {
                if (parser.parseToken("]")) {
                    terminatorFound = true;
                    break;
                }
            }

            if (pairOK) {
                var entry = Pair.deserialize(parser);
                if (entry == null) {
                    throw new IOException("Expected object entry");
                }

                map.put(entry._string, entry._value);
                pairOK = false;
                separatorOK = true;
                terminatorOK = true;
                continue;
            }

            throw new IOException("Badly-formatted object");
        }

        if (!terminatorFound) {
            throw new IOException("Unterminated object");
        }
        return new JSONObject(map);
    }

    @Override
    public void serialize(
        final OutputStream output
    ) throws IOException {
        output.write("{".getBytes(StandardCharsets.UTF_8));

        boolean first = true;
        for (var e : _values.entrySet()) {
            if (!first) {
                output.write(",".getBytes(StandardCharsets.UTF_8));
            } else {
                first = false;
            }

            output.write("\"".getBytes(StandardCharsets.UTF_8));
            output.write(e.getKey()._value.getBytes(StandardCharsets.UTF_8));
            output.write("\":".getBytes(StandardCharsets.UTF_8));
            e.getValue().serialize(output);
        }
        output.write("}".getBytes(StandardCharsets.UTF_8));
    }
}
