package com.driver;

import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository{
      HashMap<String,Order> map=new HashMap<>();


      public Order getOrderById(String id)
      {
          return map.get(id);
      }
      public void addOrder(Order id1)
      {
          map.put(id1.getId(),id1);
      }
}
