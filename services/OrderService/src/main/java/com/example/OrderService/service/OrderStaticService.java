package com.example.OrderService.service;

import com.example.common.dto.orderdtos.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderStaticService {


    List<OrderDTO> getAllOrders();

    List<OrderDTO> getAllOrdersCompleted();
}
