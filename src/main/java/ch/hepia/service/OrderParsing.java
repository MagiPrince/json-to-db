package ch.hepia.service;

import javax.json.*;

import ch.hepia.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OrderParsing{
    
    public JsonArray arrayJson;

    public OrderParsing() {

      ClassLoader classLoader = getClass().getClassLoader();
      var url = classLoader.getResource("orders.json");
      File file = new File(url.getFile());

      try(InputStream input = new FileInputStream(file)) {
        JsonReader reader = Json.createReader(input);
        JsonArray orders = reader.readArray();
        
        arrayJson = orders;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    public Customer getCustomer(int index){
      Customer customerObj;
      try{

        String id = "";
        String pseudo = "";
        String created = "";
        Short postalCode = 0;
        JsonObject obj = (JsonObject)arrayJson.get(index);
        String customer = obj.get("customer").toString();
        String shipping = obj.get("shipping").toString();
        customer = customer.replace("{", "");
        customer = customer.replace("}", "");
        customer = customer.replace("\"", "");

        shipping = shipping.replace("{", "");
        shipping = shipping.replace("}", "");
        shipping = shipping.replace("\"", "");
        List<String> customerList = new ArrayList<String>(Arrays.asList(customer.split(",")));
        List<String> shippingList = new ArrayList<String>(Arrays.asList(shipping.split(",")));

        List<String> finalListForCustomer = new ArrayList<String>(customerList);
        finalListForCustomer.addAll(shippingList);
        
        for (String listElement : finalListForCustomer) {
          List<String> currentElement = new ArrayList<String>(Arrays.asList(listElement.split(":")));
          if(currentElement.get(0).equals("id")){
            id = currentElement.get(1);
            if (id.equals("$numberLong")){
              id = currentElement.get(2);
            }
          }
          else if(currentElement.get(0).equals("pseudo")){
            pseudo = currentElement.get(1);
          }
          else if(currentElement.get(0).equals("created")){
            created = currentElement.get(1);
          }
          else if(currentElement.get(0).equals("postalCode")){
            postalCode = Short.parseShort(currentElement.get(1));
          }
        }
        
        customerObj = new Customer(id, pseudo, created, postalCode);

        return customerObj;
          
      }catch(Exception e) {
          System.out.println("Failed: " + e.getMessage());
          customerObj = new Customer("", "", "", (short)0);
          return customerObj;
        }
    }

    // Return all the vendors of a given index
    public List<Vendor> getVendors(int index){
      List<Vendor> vendorsList = new ArrayList<Vendor>();
      try{

        JsonObject obj = (JsonObject)arrayJson.get(index);
        String vendors = obj.get("vendors").toString();

        vendors = vendors.replace("{", "");
        vendors = vendors.replace("\"", "");
        vendors = vendors.replace("[", "");
        vendors = vendors.replace("]", "");

        List<String> allVendorsStringList = new ArrayList<String>(Arrays.asList(vendors.split("}")));
        
        for (String listElement : allVendorsStringList) {
          List<String> vendorStringElements = new ArrayList<String>(Arrays.asList(listElement.split(",")));
          List<String> vendorSlug;
          if (vendorStringElements.get(0).equals("") && vendorStringElements.size() > 1){
            vendorSlug = new ArrayList<String>(Arrays.asList(vendorStringElements.get(1).split(":")));
            if (vendorSlug.size() == 2){
              String slug = vendorSlug.get(1);
              vendorsList.add(new Vendor(slug));
            }
          }
          else{
            vendorSlug = new ArrayList<String>(Arrays.asList(vendorStringElements.get(0).split(":")));
            if (vendorSlug.size() == 2){
              String slug = vendorSlug.get(1);
              vendorsList.add(new Vendor(slug));
            }
          }
        }

        return vendorsList;
          
      }catch(Exception e) {
          System.out.println("Failed: " + e.getMessage());
          return vendorsList;
        }
    }

    public List<Item> getItems(int index){
      List<Item> itemList = new ArrayList<Item>();
      try{

        JsonObject obj = (JsonObject)arrayJson.get(index);
        String items = obj.get("items").toString();

        items = items.replace("{", "");
        items = items.replace("\"", "");
        items = items.replace("[", "");
        items = items.replace("]", "");

        List<String> allItemsStringList = new ArrayList<String>(Arrays.asList(items.split("}")));
        
        for (String listElement : allItemsStringList) {
          List<String> itemsStringList = new ArrayList<String>(Arrays.asList(listElement.split(",")));
          String title = "";
          int sku = 0;
          String image = "";
          float estimatedPrice = 0.0f;
          String vendor = "";
          String category = "";
          for(String eachElement : itemsStringList){
            List<String> currentElement = new ArrayList<String>(Arrays.asList(eachElement.split(":")));
            if(currentElement.size() > 1){
              if(currentElement.get(0).equals("title")){
                title = currentElement.get(1);
              }
              else if(currentElement.get(0).equals("sku")){
                sku = Integer.parseInt(currentElement.get(1));
              }
              else if(currentElement.get(0).equals("image")){
                image = currentElement.get(1);
              }
              else if(currentElement.get(0).equals("estimatedprice")){
                estimatedPrice = Float.parseFloat(currentElement.get(1));
              }
              else if(currentElement.get(0).equals("vendor")){
                vendor = currentElement.get(1);
              }
              else if(currentElement.get(0).equals("category")){
                category = currentElement.get(1);
              }
            }
          }
          itemList.add(new Item(title, sku, image, estimatedPrice, vendor, category));
        }

        return itemList;
          
      }catch(Exception e) {
          System.out.println("Failed: " + e.getMessage());
          itemList.add(new Item("", 0, "", 0.0f, "", ""));
          return itemList;
        }
    }

    public List<Item> getOrderItems(int index){
      List<Item> itemList = new ArrayList<Item>();
      try{

        JsonObject obj = (JsonObject)arrayJson.get(index);
        String items = obj.get("items").toString();

        items = items.replace("{", "");
        items = items.replace("\"", "");
        items = items.replace("[", "");
        items = items.replace("]", "");

        List<String> allItemsStringList = new ArrayList<String>(Arrays.asList(items.split("}")));
        
        for (String listElement : allItemsStringList) {
          List<String> itemsStringList = new ArrayList<String>(Arrays.asList(listElement.split(",")));
          int sku = 0;
          int qty = 0;
          String status = "";
          float finalPrice = 0.0f;
          for(String eachElement : itemsStringList){
            List<String> currentElement = new ArrayList<String>(Arrays.asList(eachElement.split(":")));
            if(currentElement.size() > 1){
              if(currentElement.get(0).equals("sku")){
                sku = Integer.parseInt(currentElement.get(1));
              }
              else if(currentElement.get(0).equals("qty")){
                qty = Integer.parseInt(currentElement.get(1));
              }
              else if(currentElement.get(0).equals("status")){
                status = currentElement.get(1);
              }
              else if(currentElement.get(0).equals("finalprice")){
                finalPrice = Float.parseFloat(currentElement.get(1));
              }
            }
          }
          itemList.add(new Item(sku, qty, status, finalPrice));
        }

        return itemList;
          
      }catch(Exception e) {
          System.out.println("Failed: " + e.getMessage());
          itemList.add(new Item(0, 0, "", 0.0f));
          return itemList;
        }
    }

    public Order getOrder(int index){
      Order orderList;
      List<Item> items = null;
      int oid = 0;
      String idCustomer = "";
      String when = "";
      Optional<Integer> bags = Optional.empty();
      
      try{

        JsonObject obj = (JsonObject)arrayJson.get(index);
        oid = Integer.parseInt(obj.get("oid").toString());

        items = getOrderItems(index);

        String customer = obj.get("customer").toString();
        String shipping = obj.get("shipping").toString();
        customer = customer.replace("{", "");
        customer = customer.replace("}", "");
        customer = customer.replace("\"", "");

        shipping = shipping.replace("{", "");
        shipping = shipping.replace("}", "");
        shipping = shipping.replace("\"", "");
        List<String> customerList = new ArrayList<String>(Arrays.asList(customer.split(",")));
        List<String> shippingList = new ArrayList<String>(Arrays.asList(shipping.split(",")));

        List<String> finalListForCustomer = new ArrayList<String>(customerList);
        finalListForCustomer.addAll(shippingList);
        
        for (String listElement : finalListForCustomer) {
          List<String> currentElement = new ArrayList<String>(Arrays.asList(listElement.split(":")));
          if(currentElement.get(0).equals("id")){
            idCustomer = currentElement.get(1);
          }
          else if(currentElement.get(0).equals("when")){
            when = currentElement.get(1) + ":" + currentElement.get(2) + ":" + currentElement.get(3);
          }
          else if(currentElement.get(0).equals("bags")){
            bags = Optional.of(Integer.parseInt(currentElement.get(1)));
          }
        }

        orderList = new Order(oid, idCustomer, when, bags, items);

        return orderList;
          
      }catch(Exception e) {
          System.out.println("Failed: " + e.getMessage());
          orderList = new Order(oid, idCustomer, when, bags, items);
          return orderList;
        }
    }

    public List<Customer> getCustomerList(){
      ArrayList<Customer> customerList = new ArrayList<Customer>();

      for(int i = 0; i < arrayJson.size(); i++){
        Customer c = getCustomer(i);
        if (! customerList.contains(c)){
          customerList.add(c);          
        }
      }
      return customerList;
    }

    public List<Vendor> getVendorsList(){
      ArrayList<Vendor> vendorList = new ArrayList<Vendor>();

      for(int i = 0; i < arrayJson.size(); i++){
        List<Vendor> vendors = getVendors(i);
        vendors.forEach(v -> {
          if (!vendorList.contains(v)){
            vendorList.add(v);
          }
        });
      }
      return vendorList;
    }

    public List<Item> getItemsList(){
      ArrayList<Item> itemList = new ArrayList<Item>();

      for(int i = 0; i < arrayJson.size(); i++){
        List<Item> items = getItems(i);
        items.forEach(item -> {
          if (! itemList.contains(item)){
            itemList.add(item);
          }
        });
      }
      return itemList;
    }

    public List<Order> getOrderList(){
      ArrayList<Order> orderList = new ArrayList<Order>();

      for(int i = 0; i < arrayJson.size(); i++){
        orderList.add(getOrder(i));
      }
      return orderList;
    }

}