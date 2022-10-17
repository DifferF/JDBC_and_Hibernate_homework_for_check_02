package homework.task_03;

public class Flowers implements Comparable<Flowers> {

    private String flowers_name;
    private int price;
    private int freshness;
    private String accessories_name;

    private int stem; // длина стебля
    public int getFreshness() {
        return freshness;
    }

    public void setFreshness(int freshness) {
        this.freshness = freshness;
    }

    public int getStem() {
        return stem;
    }

    public void setStem(int stem) {
        this.stem = stem;
    }

    public Flowers(String flowers_name, int price, int freshness, int stem) {
        this.flowers_name = flowers_name;
        this.price = price;
        this.freshness = freshness;
        this.stem = stem;
    }

    public Flowers(int price, String accessories_name) {
        this.accessories_name = accessories_name;
        this.price = price;
    }

    public String getAccessories_name() {
        return accessories_name;
    }

    public void setAccessories_name(String accessories_name) {
        this.accessories_name = accessories_name;
    }

    public String getFlowers_name() {
        return flowers_name;
    }

    public void setFlowers_name(String flowers_name) {
        this.flowers_name = flowers_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int compareTo(Flowers o) {
        {
            if (this.freshness != o.getFreshness()) {
                return this.freshness - o.getFreshness();
            }
            return this.flowers_name.compareTo(o.getFlowers_name());
        }
    }

    @Override
    public String toString() {
        return  flowers_name +  " свежесть " + freshness ;
    }
}
