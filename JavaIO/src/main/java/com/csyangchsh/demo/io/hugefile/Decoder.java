package com.csyangchsh.demo.io.hugefile;

import java.nio.ByteBuffer;

/**
 *
 * Date: 14/8/25
 */
public interface Decoder<T> {
    public T decode(ByteBuffer buffer);
}
