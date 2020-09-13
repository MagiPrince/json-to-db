package ch.hepia;

import javax.json.*;
import ch.hepia.service.*;
import ch.hepia.service.OrderParsing;
import ch.hepia.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {

  public static void main(String[] args) {
    OrderParsing orderParsing = new OrderParsing();
    
    List<Customer> customers = orderParsing.getCustomerList();
    List<Vendor> vendors = orderParsing.getVendorsList();
    List<Item> items = orderParsing.getItemsList();
    List<Order> orders = orderParsing.getOrderList();
    

    OrderRepositoryWriteSide write = new OrderRepositoryWriteSide();
    write.insertCustomers(customers);
    write.insertVendors(vendors);
    write.insertItems(items);
    write.insertOrders(orders);

    
    OrderRepositoryReadSide read = new OrderRepositoryReadSide();
    read.customers()
      .stream()
      .forEach( c -> System.out.println(c.getPseudo()) );

    read.vendors()
      .stream()
      .forEach(v -> System.out.println(v.getSlug()));

    read.ordersOf(read.customers().get(0))
        .stream()
        .forEach(o -> System.out.println(o.getOid()));

    read.ordersOf(read.vendors().get(0))
        .stream()
        .forEach(o -> System.out.println(o.getOid()));
  }
}