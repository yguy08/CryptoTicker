package com.tapereader.framework;

import com.tapereader.model.Order;

public interface OrderEventListener {
    void onOrder(Order order);
}
