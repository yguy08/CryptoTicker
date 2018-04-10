package com.tickercash.tapereader.listener;

import com.tickercash.tapereader.model.Tip;

public interface TipEventListener {
    void onTip(Tip tip);
}
