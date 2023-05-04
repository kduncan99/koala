/*
 * Simple JSON library
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.json;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class JSONNull extends JSONValue {

    public JSONNull(){}

    public static JSONNull deserialize(
        final Parser parser
    ) {
        return parser.parseToken("null") ? new JSONNull() : null;
    }

    @Override
    public void serialize(
        final OutputStream output
    ) throws IOException {
        output.write("null".getBytes(StandardCharsets.UTF_8));
    }
}
