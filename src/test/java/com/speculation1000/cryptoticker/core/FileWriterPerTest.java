package com.speculation1000.cryptoticker.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class FileWriterPerTest {

private static final int ITERATIONS = 5;
private static final double MEG = (Math.pow(1024, 2));
private static final int RECORD_COUNT = 100_000;
private static final String RECORD = "BTC/USDT,123405968,10000.00,10000.01,10000.0233333,10000.03333333,10000.0333333";
private static final int RECSIZE = RECORD.getBytes().length;

public static void main(String[] args) throws Exception {
    List<String> records = new ArrayList<String>(RECORD_COUNT);
    int size = 0;
    for (int i = 0; i < RECORD_COUNT; i++) {
        records.add(RECORD);
        size += RECSIZE;
    }
    System.out.println(records.size() + " 'records'");
    System.out.println(size / MEG + " MB");

    for (int i = 0; i < ITERATIONS; i++) {
        System.out.println("\nIteration " + i);

        writeRaw(records);
        writeBuffered(records, 8192);
        writeBuffered(records, (int) MEG);
        writeBuffered(records, 4 * (int) MEG);
    }
}

private static void writeRaw(List<String> records) throws IOException {
    File file = File.createTempFile("foo", ".txt");
    try {
        FileWriter writer = new FileWriter(file);
        System.out.print("Writing raw... ");
        write(records, writer);
    } finally {
        // comment this out if you want to inspect the files afterward
        file.delete();
    }
}

public static void writeBuffered(List<String> records, int bufSize) throws IOException {
    File file = File.createTempFile("foo", ".txt");
    try {
        FileWriter writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);

        System.out.print("Writing buffered (buffer size: " + bufSize + ")... ");
        write(records, bufferedWriter);
    } finally {
        // comment this out if you want to inspect the files afterward
        file.delete();
    }
}

public static String writeBuffered(String record, int bufSize,File file,FileWriter writer) throws IOException {
    try {
        BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);

        System.out.println("Writing buffered (buffer size: " + bufSize + ")... ");
        return write(record, bufferedWriter);
    } finally {
        // comment this out if you want to inspect the files afterward
        file.delete();
    }
}

public static String write(String record, Writer writer) throws IOException {
    long start = System.currentTimeMillis();
    writer.write(record);
    //writer.flush();
    //writer.close();
    long end = System.currentTimeMillis();
    return record;
    //System.out.println((end - start) / 1000f + " seconds");
}

public static void write(List<String> records, Writer writer) throws IOException {
    long start = System.currentTimeMillis();
    for (String record: records) {
        writer.write(record);
    }
    //writer.flush();
    //writer.close();
    long end = System.currentTimeMillis();
    System.out.println((end - start) / 1000f + " seconds");
}
}
