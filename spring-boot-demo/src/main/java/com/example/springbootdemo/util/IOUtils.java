package com.example.springbootdemo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Throwable var2 = null;

        byte[] var3;
        try {
            copy((InputStream)input, (OutputStream)output);
            var3 = output.toByteArray();
        } catch (Throwable var12) {
            var2 = var12;
            throw var12;
        } finally {
            if (output != null) {
                if (var2 != null) {
                    try {
                        output.close();
                    } catch (Throwable var11) {
                        var2.addSuppressed(var11);
                    }
                } else {
                    output.close();
                }
            }

        }

        return var3;
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int)count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, 8192);
    }

    public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0L;
        int n;
        if (input != null) {
            while(-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += (long)n;
            }
        }
        return count;
    }
}
