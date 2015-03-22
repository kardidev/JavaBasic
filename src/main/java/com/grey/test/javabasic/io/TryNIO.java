package com.grey.test.javabasic.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystem;
import java.util.Arrays;

/**
 * Created by Grey on 22.03.2015.
 */
public class TryNIO {

    public static void testIt() throws IOException, InterruptedException {

        RandomAccessFile file = new RandomAccessFile("g:/java/2.bin", "rw");
        FileChannel channel = file.getChannel();

        System.out.println(channel.size());

        FileLock lock = channel.lock();
        write(channel);
        lock.release();
        lock.close();

        lock = channel.tryLock(0, 4, false);

        System.out.println("Thread 1 started");
        read(channel, 0, 4).join();
        System.out.println("Thread 1 completed");
        System.out.println("Thread 2 started");
        read(channel, 4, 5).join();
        System.out.println("Thread 2 completed");

        lock.release();
        lock.close();
        channel.close();
    }

    private static void write(FileChannel channel) throws IOException {
        ByteBuffer buf = ByteBuffer.wrap(new String("123456789").getBytes("UTF-8"));
        channel.write(buf);
    }

    private static Thread read(final FileChannel channel, final int offset, final int length) {

        Thread thread = new Thread() {

            @Override
            public void run() {
                try {

                    FileLock lock = channel.tryLock(offset, length, false);
                    ByteBuffer buf = ByteBuffer.allocate(length);
                    channel.position(offset);
                    channel.read(buf);
                    lock.release();
                    lock.close();
                    System.out.println(Arrays.toString(buf.array()));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        };

        thread.start();
        return thread;
    }

}
