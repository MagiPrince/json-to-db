package ch.hepia;

import java.util.Optional;

import java.util.List;

public class Order {
    private int oid;
    private String customer;
    private String when;
    private Optional<Integer> bags;
    private List<Item> items;
    
    public Order(int oid, String customer, String when, Optional<Integer> bags, List<Item> items){
        this.oid = oid;
        this.customer = customer;
        this.when = when;
        this.bags = bags;
        this.items = items;
    }

    public int getOid(){
        return this.oid;
    }

    public String getCustomer(){
        return this.customer;
    }

    public String getWhen(){
        return this.when;
    }

    public Optional<Integer> getBags(){
        return this.bags;
    }

    public List<Item> getItems(){
        return this.items;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        Order order = (Order) o;
        return order.getOid() == this.oid;
    }
}
