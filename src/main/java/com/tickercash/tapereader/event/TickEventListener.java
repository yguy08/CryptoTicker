package com.tickercash.tapereader.event;

import com.tickercash.tapereader.model.Tick;

public interface TickEventListener {
	void onTick(Tick tick);
}
