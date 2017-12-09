package com.tickercash.tapereader.tape;

import java.util.Properties;

import com.tickercash.tapereader.core.UniqueCurrentTimeMS;

import net.openhft.chronicle.bytes.Bytes;

public class FakeTape extends Tape {
	
	private Bytes<?> bytes = Bytes.elasticByteBuffer();
	
	@Override
	public void start() throws Exception {
		disruptor.start();
        while(true){
        	onTick(bytes
					.append("BTC/USDT").append(' ')
					.append(UniqueCurrentTimeMS.uniqueCurrentTimeMS()).append(' ')
					.append(10000.00).append(' ')
					.append(10000.00).append(' ')
					.append(10000.00).append(' ')
					.append(500000.00).append(' '));
        }
	}

	@Override
	public void configure(Properties props) throws Exception {

	}

}
