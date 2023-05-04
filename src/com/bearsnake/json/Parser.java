/*
 * Simple JSON library
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.bearsnake.json;

import java.io.IOException;

public class Parser {

    private final String _text;
    private int _index;

    public Parser(
        final String text
    ) {
        _text = text;
        _index = 0;
    }

    boolean atEnd() {
        return _index >= _text.length();
    }

    char getNextChar() {
        char ch = 0;
        if (!atEnd()) {
            ch = _text.charAt(_index);
            _index++;
        }
        return ch;
    }

    private boolean isWhiteSpace(
        final Character ch
    ) {
        return ((ch == 0x20) || (ch == 0x0a) || (ch == 0x0d) || (ch == 0x09));
    }

    char parseDigit() {
        if (atEnd()) {
            return 0;
        }

        char ch = _text.charAt(_index);
        if (!Character.isDigit(ch)) {
            return 0;
        }

        _index++;
        return ch;
    }

    boolean parseToken(
        final String token
    ) {
        if (token.length() > remaining()) {
            return false;
        }

        for (int tx = 0, ty = _index; tx < token.length(); tx++, ty++) {
            if (token.charAt(tx) != _text.charAt(ty)) {
                return false;
            }
        }

        _index += token.length();
        return true;
    }

    private char peekNextChar() throws IOException {
        if (atEnd()) {
            throw new IOException("No more data");
        } else {
            return _text.charAt(_index);
        }
    }

    private int remaining() {
        return _text.length() - _index;
    }

    void skipWhiteSpace() {
        while (!atEnd()) {
            if (isWhiteSpace(_text.charAt(_index))) {
                _index++;
            }
        }
    }
}
