package com.csyangchsh.demo.io.hugefile;

import com.google.common.io.Closeables;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Date: 14/8/25
 */
public class FileReader<T> implements Iterable<List<T>> {
    private static final long CHUNK_SIZE = 4096;
    private final Decoder<T> decoder;
    private Iterator<File> files;

    private FileReader(Decoder<T> decoder, File... files) {
        this(decoder, Arrays.asList(files));
    }
    private FileReader(Decoder<T> decoder, List<File> files) {
        this.files = files.iterator();
        this.decoder = decoder;
    }
    public static <T> FileReader<T> create(Decoder<T> decoder, List<File> files) {
        return new FileReader<T>(decoder, files);
    }

    public static <T> FileReader<T> create(Decoder<T> decoder, File... files) {
        return new FileReader<T>(decoder, files);
    }
    @Override
    public Iterator<List<T>> iterator() {
        return new Iterator<List<T>>() {
            private List<T> entries;
            private long chunkPos = 0;
            private MappedByteBuffer buffer;
            private FileChannel channel;
            @Override
            public boolean hasNext() {
                if (buffer == null || !buffer.hasRemaining()) {
                    buffer = nextBuffer(chunkPos);
                    if (buffer == null) {
                        return false;
                    }
                }
                T result = null;
                while ((result = decoder.decode(buffer)) != null) {
                    if (entries == null) {
                        entries = new ArrayList<T>();
                    }
                    entries.add(result);
                }
                // set next MappedByteBuffer chunk
                chunkPos += buffer.position();
                buffer = null;
                if (entries != null) {
                    return true;
                } else {
                    try {
                        Closeables.close(channel, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }

            private MappedByteBuffer nextBuffer(long position) {
                try {
                    if (channel == null || channel.size() == position) {
                        if (channel != null) {
                            Closeables.close(channel, true);
                            channel = null;
                        }
                        if (files.hasNext()) {
                            File file = files.next();
                            channel = new RandomAccessFile(file, "r").getChannel();
                            chunkPos = 0;
                            position = 0;
                        } else {
                            return null;
                        }
                    }
                    long chunkSize = CHUNK_SIZE;
                    if (channel.size() - position < chunkSize) {
                        chunkSize = channel.size() - position;
                    }
                    return channel.map(FileChannel.MapMode.READ_ONLY, chunkPos, chunkSize);
                } catch (IOException e) {
                    try {
                        Closeables.close(channel, true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    throw new RuntimeException(e);
                }
            }

            @Override
            public List<T> next() {
                List<T> res = entries;
                entries = null;
                return res;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
