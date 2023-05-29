package com.test.jms.OrderProcessingSystem.controller;

import com.test.jms.OrderProcessingSystem.model.OrderDetail;
import com.test.jms.OrderProcessingSystem.service.OrderAcknowledgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;
import javax.naming.NamingException;

@RestController
public class OrderAcknowledgement {
    @Autowired
    public OrderAcknowledgementService service;

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(@RequestBody OrderDetail order) throws NamingException, JMSException {
        String orderStatus = service.createOrder(order);
        return ResponseEntity.ok(orderStatus);
    }
}
