package ch.hepia;

public class Item{
    private String title;
    private int sku;
    private String image;
    private int qty;
    private float estimatedPrice;
    private float finalPrice;
    private String vendor;
    private String category;
    private String status;

    public Item(String title, int sku, String image, float estimatedPrice, String vendor, String category){
        this.title = title;
        this.sku = sku;
        this.image = image;
        this.estimatedPrice = estimatedPrice;
        this.vendor = vendor;
        this.category = category;
    }

    public Item(int sku, int qty, String status, float finalPrice){
        this.sku = sku;
        this.qty = qty;
        this.status = status;
        this.finalPrice = finalPrice;
    }

    public String getTitle(){
        return this.title;
    }

    public int getSku(){
        return this.sku;
    }

    public String getImage(){
        return this.image;
    }

    public float getEstimatedPrice(){
        return this.estimatedPrice;
    }

    public float getFinalPrice(){
        return this.finalPrice;
    }

    public String getVendor(){
        return this.vendor;
    }

    public String getCategory(){
        return this.category;
    }

    public int getQty(){
        return this.qty;
    }

    public String getStatus(){
        return this.status;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        Item i = (Item) o;
        return i.getSku() == this.sku;
    }
}