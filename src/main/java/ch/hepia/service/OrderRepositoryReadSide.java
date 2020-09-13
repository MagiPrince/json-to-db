package ch.hepia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;
import ch.hepia.*;

public class OrderRepositoryReadSide {

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/orders?useSSL=false";
    private String user = "root";
    private String password = "test";

    public List<Customer> customers(){

        List<Customer> customers = new ArrayList<Customer>();

        String query = "SELECT * FROM customer;";
        try (Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String pseudo = rs.getString("pseudo");
                String created = rs.getString("created");
                Short postalCode = rs.getShort("postalCode");
                customers.add(new Customer(id, pseudo, created, postalCode));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<Vendor> vendors(){
        List<Vendor> vendors = new ArrayList<Vendor>();

        String query = "SELECT * FROM vendor;";
        try (Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String slug = rs.getString("slug");
                vendors.add(new Vendor(slug));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendors;
    }

    public List<Order> ordersOf(Customer customer){
        String id = customer.getId();

        List<Order> orders = new ArrayList<Order>();

        String query = "SELECT order_table.oid, order_table.when, order_table.bags, order_table.customer_id FROM order_table " 
        + "WHERE order_table.customer_id = " + id + ";";
        try (Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int oid = rs.getInt("oid");
                String when = rs.getString("when");
                Optional<Integer> bags = Optional.ofNullable(rs.getInt("bags"));
                String customerId = rs.getString("customer_id");
                orders.add(new Order(oid, customerId, when, bags, new ArrayList<Item>()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> ordersOf(Vendor vendor){
        String slug = vendor.getSlug();

        List<Order> orders = new ArrayList<Order>();

        String query = "SELECT order_table.oid, order_table.when, order_table.bags, order_table.customer_id FROM order_table"
        + " INNER JOIN order_item ON order_table.oid = order_item.oid INNER JOIN item ON order_item.sku = item.sku " 
        + "WHERE item.slug = '" + slug + "';";
        try (Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int oid = rs.getInt("oid");
                String when = rs.getString("when");
                Optional<Integer> bags = Optional.ofNullable(rs.getInt("bags"));
                String customer = rs.getString("customer_id");
                orders.add(new Order(oid, customer, when, bags, new ArrayList<Item>()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
