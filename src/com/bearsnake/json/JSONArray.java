/*
 * Simple JSON library
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.json;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JSONArray extends JSONValue {

    private final ArrayList<JSONValue> _values = new ArrayList<>();

    public JSONArray(
        final ArrayList<JSONValue> value
    ) {
        _values.addAll(value);
    }

    public static JSONArray deserialize(
        final Parser parser
    ) throws IOException {
        if (!parser.parseToken("[")) {
            return null;
        }

        var list = new ArrayList<JSONValue>();
        boolean valueOK = true;
        boolean separatorOK = false;
        boolean terminatorOK = true;
        boolean terminatorFound = false;
        while (!parser.atEnd()) {
            parser.skipWhiteSpace();
            if (separatorOK) {
                if (parser.parseToken(",")) {
                    separatorOK = false;
                    terminatorOK = false;
                    valueOK = true;
                    continue;
                }
            }

            if (terminatorOK) {
                if (parser.parseToken("]")) {
                    terminatorFound = true;
                    break;
                }
            }

            if (valueOK) {
                var value = JSONValue.deserialize(parser);
                if (value != null) {
                    list.add(value);
                    separatorOK = true;
                    terminatorOK = true;
                    valueOK = false;
                    continue;
                }
            }

            throw new IOException("Badly-formatted array");
        }

        if (!terminatorFound) {
            throw new IOException("Unterminated array");
        }
        return new JSONArray(list);
    }

    @Override
    public void serialize(
        final OutputStream output
    ) throws IOException {
        output.write("[".getBytes(StandardCharsets.UTF_8));
        for (int x = 0; x < _values.size(); ++x) {
            if (x > 0) {
                output.write(",".getBytes(StandardCharsets.UTF_8));
            }
            _values.get(x).serialize(output);
        }
        output.write("]".getBytes(StandardCharsets.UTF_8));
    }
}
