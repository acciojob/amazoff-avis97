package com.driver;

import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository{
      HashMap<String,Order> orderDb=new HashMap<>();
      HashMap<String,DeliveryPartner> partnerDb=new HashMap<>();
      HashMap<String,String>  orderPartnerDb=new HashMap<>();
      HashMap<String,List<String>> partnerOrderDb=new HashMap<>();

      public void addOrder(Order order)
      {
            orderDb.put(order.getId(),order);
      }
      public void addPartner(String Pid)
      {
            partnerDb.put(Pid,new DeliveryPartner(Pid));
      }
      public void addOrderPartnerPair(String orderId, String partnerId){

            if(orderDb.containsKey(orderId) && partnerDb.containsKey(partnerId))
            {
                  orderPartnerDb.put(orderId,partnerId);

                  List<String> currentOrder=new ArrayList<>();
                  if(partnerOrderDb.containsKey(partnerId))
                  {
                        currentOrder=partnerOrderDb.get(partnerId);
                  }
                  currentOrder.add(orderId);
                  partnerOrderDb.put(partnerId,currentOrder);
                  DeliveryPartner deliveryPartner=partnerDb.get(partnerId);
                  deliveryPartner.setNumberOfOrders(currentOrder.size());
            }
      }
      public Order getOrderById(String orderId){
            return orderDb.get(orderId);
      }

      public DeliveryPartner getPartnerById(String partnerId){
             return partnerDb.get(partnerId);
      }

      public Integer getOrderCountByPartnerId(String partnerId) {
            return partnerOrderDb.get(partnerId).size();
      }

      public List<String> getOrdersByPartnerId(String partnerId){
            return partnerOrderDb.get(partnerId);
      }

      public List<String> getAllOrder(){

            List<String> order=new ArrayList<>();
            for(String key:orderDb.keySet())
            {
                  order.add(key);
            }
            return order;
      }

      public int getCountOfUnassignedOrders(){
            return orderDb.size()-orderPartnerDb.size();
      }

      public Integer getOrdersLeftAfterGivenTimeByPartnerId(int newTime, String partnerId) {
            int count=0;
            List<String> orders=partnerOrderDb.get(partnerId);
            for(String orderId:orders)
            {
                  int deliveryTime=orderDb.get(orderId).getDeliveryTime();
                  if(deliveryTime>newTime)
                  {
                        count++;
                  }
            }
            return count;
      }

      public int getLastDeliveryTimeByPartnerId(String partnerId){
            int maxTime=0;
            List<String> orders=partnerOrderDb.get(partnerId);
            for(String orderId:orders)
            {
                  int currentTime=orderDb.get(orderId).getDeliveryTime();
                  maxTime=Math.max(maxTime,currentTime);
            }
            return maxTime;
      }

      public void deletePartnerById(String partnerId){

            partnerDb.remove(partnerId);
            List<String> listOforder=partnerOrderDb.get(partnerId);
            partnerOrderDb.remove(partnerId);

            for(String order:listOforder)
            {
                  orderPartnerDb.remove(order);
            }
      }

      public void deleteOrderById(String orderId){
            orderDb.remove(orderId);

            String partnerId=orderPartnerDb.get(orderId);
            orderPartnerDb.remove(orderId);

            partnerOrderDb.get(partnerId).remove(orderId);
            partnerDb.get(partnerId).setNumberOfOrders(partnerOrderDb.get(partnerId).size());
      }

}
