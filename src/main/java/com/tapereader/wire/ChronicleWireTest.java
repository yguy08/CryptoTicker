package com.tapereader.wire;

import java.io.IOException;
import java.nio.ByteBuffer;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.wire.AbstractMarshallable;
import net.openhft.chronicle.wire.TextWire;
import net.openhft.chronicle.wire.Wire;

public class ChronicleWireTest {
    
    public ChronicleWireTest(){
        
    }

    public static void main(String[] args) throws IOException {
        Bytes<ByteBuffer> bytes = Bytes.elasticByteBuffer();
        Wire wire = new TextWire(bytes);
        
        Data quote = new Data("BTC", 10000L, TimeUnit.SECONDS, 1000.00);
        
        quote.writeMarshallable(wire);
        System.out.println(bytes);
        
        Data quote2 = new Data();
        quote2.readMarshallable(wire);
        System.out.println(quote2);
    }

}

class Data extends AbstractMarshallable {
    String message;
    long number;
    TimeUnit timeUnit;
    double price;

    public Data() {
        
    }

    public Data(String message, long number, TimeUnit timeUnit, double price) {
        this.message = message;
        this.number = number;
        this.timeUnit = timeUnit;
        this.price = price;
    }
}

enum TimeUnit {
    HOURS, MINUTES, SECONDS;
}

