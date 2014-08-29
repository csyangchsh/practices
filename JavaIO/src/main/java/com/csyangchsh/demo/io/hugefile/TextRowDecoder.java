package com.csyangchsh.demo.io.hugefile;

import java.nio.ByteBuffer;

/**
 *
 * Date: 14/8/25
 */
public class TextRowDecoder implements Decoder<byte[][]> {
    private static final byte LF = 10;
    private final int numFields;
    private final byte delimiter;
    public TextRowDecoder(int numFields, byte delimiter) {
        this.numFields = numFields;
        this.delimiter = delimiter;
    }
    @Override
    public byte[][] decode(ByteBuffer buffer) {
        int lineStartPos = buffer.position();
        int limit = buffer.limit();
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            if (b == LF) { // reached line feed so parse line
                int lineEndPos = buffer.position();
                // set positions for one row duplication
                if (buffer.limit() < lineEndPos + 1) {
                    buffer.position(lineStartPos).limit(lineEndPos);
                } else {
                    buffer.position(lineStartPos).limit(lineEndPos + 1);
                }
                byte[][] entry = parseRow(buffer.duplicate());
                if (entry != null) {
                    // reset main buffer
                    buffer.position(lineEndPos);
                    buffer.limit(limit);
                    // set start after LF
                    lineStartPos = lineEndPos;
                }
                return entry;
            }
        }
        buffer.position(lineStartPos);
        return null;
    }

    public byte[][] parseRow(ByteBuffer buffer) {
        int fieldStartPos = buffer.position();
        int fieldEndPos = 0;
        int fieldNumber = 0;
        byte[][] fields = new byte[numFields][];
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            if (b == delimiter || b == LF) {
                fieldEndPos = buffer.position();
                // save limit
                int limit = buffer.limit();
                // set positions for one row duplication
                buffer.position(fieldStartPos).limit(fieldEndPos);
                fields[fieldNumber] = parseField(buffer.duplicate(), fieldNumber, fieldEndPos - fieldStartPos - 1);
                fieldNumber++;
                // reset main buffer
                buffer.position(fieldEndPos);
                buffer.limit(limit);
                // set start after LF
                fieldStartPos = fieldEndPos;
            }
            if (fieldNumber == numFields) {
                return fields;
            }
        }
        return null;
    }

    private byte[] parseField(ByteBuffer buffer, int pos, int length) {
        byte[] field = new byte[length];
        for (int i = 0; i < field.length; i++) {
            field[i] = buffer.get();
        }
        return field;
    }
}