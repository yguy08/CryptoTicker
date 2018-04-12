package com.tapereader.listener;

import com.tapereader.model.Order;

public interface OrderEventListener {
    void onOrder(Order order);
}
