package com.speculation1000.cryptoticker.model;

import org.knowm.xchange.dto.marketdata.Ticker;

public interface TickTranslator {
	void translate(Tick event, long sequence, Ticker ticker);
}
