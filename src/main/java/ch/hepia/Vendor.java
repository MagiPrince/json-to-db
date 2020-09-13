package ch.hepia;

public class Vendor{
    private String slug;

    public Vendor(String slug){
        this.slug = slug;
    }

    public String getSlug(){
        return this.slug;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        Vendor v = (Vendor) o;
        return v.getSlug().equals(this.slug);
    }
}