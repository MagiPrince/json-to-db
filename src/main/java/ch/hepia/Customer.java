package ch.hepia;

public class Customer{
    private String id;
    private String pseudo;
    private String created;
    private Short postalCode;

    public Customer(String id, String pseudo, String created, Short postalCode){
        this.id = id;
        this.pseudo = pseudo;
        this.created = created;
        this.postalCode = postalCode;
    }

    public String getId(){
        return this.id;
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public String getCreated(){
        return this.created;
    }

    public Short getPostalCode(){
        return this.postalCode;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        Customer c = (Customer) o;
        return c.getId().equals(this.id);
    }
}