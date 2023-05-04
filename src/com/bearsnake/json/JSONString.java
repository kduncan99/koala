/*
 * Simple JSON library
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.json;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class JSONString extends JSONValue {

    public final String _value;

    public JSONString(
        final String value
    ) {
        var sb = new StringBuilder();
        int vx = 0;
        while (vx < value.length()) {
            char ch = value.charAt(vx++);
            if ((ch == '\\') && (vx < value.length())) {
                ch = value.charAt(vx++);
                switch (ch) {
                    case '/', '\\', '"' -> sb.append(ch);
                    case 'b' -> sb.append(0x08);
                    case 'f' -> sb.append(0x0c);
                    case 'n' -> sb.append(0x0a);
                    case 'r' -> sb.append(0x0d);
                    case 't' -> sb.append(0x09);
                    case 'u' -> {
                        var remaining = value.length() - vx;
                        if (remaining >= 4) {
                            int hexToInt = Integer.parseInt(value.substring(vx, vx + 4), 16);
                            sb.append((char) hexToInt);
                            vx += 4;
                        } else {
                            sb.append("\\u");
                        }
                    }
                }
            } else {
                sb.append(ch);
            }
        }
        _value = sb.toString();
    }

    @Override
    public void serialize(
        final OutputStream output
    ) throws IOException {
        output.write("\"".getBytes(StandardCharsets.UTF_8));
        int vx = 0;
        while (vx < _value.length()) {
            var ch = _value.charAt(vx++);
            if (ch == '/') {
                output.write("\\/".getBytes(StandardCharsets.UTF_8));   //  solidus \/
            } else if (ch == '\\') {
                output.write("\\\\".getBytes(StandardCharsets.UTF_8));  //  reverse solidus \\
            } else if (ch == '"') {
                output.write("\\\"".getBytes(StandardCharsets.UTF_8));  //  double-quote \"
            } else if (ch == 0x08) {
                output.write("\\b".getBytes(StandardCharsets.UTF_8));   //  backspace \b
            } else if (ch == 0x0c) {
                output.write("\\f".getBytes(StandardCharsets.UTF_8));   //  form feed \f
            } else if (ch == 0x0a) {
                output.write("\\n".getBytes(StandardCharsets.UTF_8));   //  line feed \n
            } else if (ch == 0x0d) {
                output.write("\\r".getBytes(StandardCharsets.UTF_8));   //  carriage return \r
            } else if (ch == 0x09) {
                output.write("\\t".getBytes(StandardCharsets.UTF_8));   //  horizontal tab \t
            } else if ((ch >= 0x20) && (ch < 0xff)) {
                output.write(0x00); //  any normal ascii character
                output.write(ch);
            } else {
                var s = String.format("\\u%04x", (int)ch);   //  something requiring unicode backslash+uxxxx
                output.write(s.getBytes(StandardCharsets.UTF_8));
            }
        }
        output.write("\"".getBytes(StandardCharsets.UTF_8));
    }
}
