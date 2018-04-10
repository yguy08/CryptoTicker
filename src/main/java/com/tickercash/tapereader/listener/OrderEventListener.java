package com.tickercash.tapereader.listener;

import com.tickercash.tapereader.model.Order;

public interface OrderEventListener {
    void onOrder(Order order);
}
