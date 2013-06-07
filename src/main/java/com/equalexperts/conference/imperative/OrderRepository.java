package com.equalexperts.conference.imperative;

public interface OrderRepository {
    Order obtainOrder(String orderId); 
    void storeOrder(Order order);
}