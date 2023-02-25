package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository ResponseEntity;

    public void addOrder(Order id)
    {
        ResponseEntity.addOrder(id);
    }
    public void getOrderById(String id)
    {
        ResponseEntity.getOrderById(id);
    }
}
