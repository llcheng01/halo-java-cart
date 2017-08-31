package hautelook;

/**
 * Make immutable
 *
 * Created by johnnycheng on 8/30/17.
 */
public final class Item {
    private final String name;
    private final double price;
    private final int weight;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        this.weight = 0;
    }

    public Item(String name, double price, int weight){
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getWeight() { return this.weight; }
 }
