package ch.hepia.service;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import ch.hepia.*;

public class OrderRepositoryWriteSide {
    
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/orders?useSSL=false";
    private String user = "root";
    private String password = "test";

    public void insertOrders(List<Order> orders){
        try {
            Class.forName(this.driver);
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);

            orders.forEach(o -> {
                String queryOrder;
                try{
                    PreparedStatement ps;
                    if (o.getBags().isPresent()){
                        queryOrder = "INSERT INTO order_table (oid, `when`, bags, customer_id) VALUES (?, ?, ?, ?);";
                        ps = connection.prepareStatement(queryOrder);
                        ps.setInt(1, o.getOid());
                        ps.setString(2, o.getWhen());
                        ps.setInt(3, o.getBags().get());
                        ps.setString(4, o.getCustomer());
                    }else{
                        queryOrder = "INSERT INTO order_table (oid, `when`, customer_id) VALUES (?, ?, ?);";
                        ps = connection.prepareStatement(queryOrder);
                        ps.setInt(1, o.getOid());
                        ps.setString(2, o.getWhen());
                        ps.setString(3, o.getCustomer());
                    }
                    ps.executeUpdate();
                    o.getItems().forEach(i ->
                    {
                    PreparedStatement psItem;
                    try{
                            String queryItem = "INSERT INTO order_item (sku, oid, qty, status, finalprice) VALUES (?, ?, ?, ?, ?);";
                            psItem = connection.prepareStatement(queryItem);
                            psItem.setInt(1, i.getSku());
                            psItem.setInt(2, o.getOid());
                            psItem.setInt(3, i.getQty());
                            psItem.setString(4, i.getStatus());
                            psItem.setFloat(5, i.getFinalPrice());
                            psItem.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (ClassNotFoundException e) { 
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void insertCategory(String category){
        try {
            Class.forName(this.driver);
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
            String query = "INSERT INTO category (name) VALUES (?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, category);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) { 
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void insertItems(List<Item> items){
        try {
            Class.forName(this.driver);
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
            List<String> categories = new ArrayList<String>();
            items.forEach(i -> {
                if (! categories.contains(i.getCategory())){
                    categories.add(i.getCategory());
                    insertCategory(i.getCategory());
                }
                String query = "INSERT INTO item (title, sku, image, estimatedprice, slug, category)"
                + " VALUES ('" + i.getTitle().replace("'", "''") + "', '" + i.getSku() + "', '" + i.getImage() + "', '" + i.getEstimatedPrice() + "', '" + i.getVendor() + "', '" + i.getCategory() + "');";
                try{
                    connection.createStatement().executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (ClassNotFoundException e) { 
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void insertVendors(List<Vendor> vendors){
        try {
            Class.forName(this.driver);
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);

            vendors.forEach(v -> {
                String query = "INSERT INTO vendor (slug)"
                + " VALUES ('" + v.getSlug() + "');";
                try{
                    connection.createStatement().executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (ClassNotFoundException e) { 
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void insertCustomers(List<Customer> customers){
        try {
            Class.forName(this.driver);
            Connection connection = DriverManager.getConnection(this.url, this.user, this.password);

            customers.forEach(c -> {
                String query = "INSERT INTO customer (id, pseudo, created, postalCode)"
                + " VALUES ('" + c.getId() + "', '" + c.getPseudo() + "', '" + c.getCreated() + "', '" + c.getPostalCode() + "');";
                try{
                    connection.createStatement().executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (ClassNotFoundException e) { 
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}