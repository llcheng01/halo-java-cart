package hautelook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bill on 4/10/17.
 */
public class Cart {
    private static final double INITIAL_CART_SUBTOTAL = 0.0;
    private static final double DEFAULT_COUPON_VALUE = 0.0;
    private static final int INITIAL_CART_WEIGHT = 0;
    private static final int FLAT_SHIPPING_RATE = 5;
    private static final int PER_ITEM_SHIPPING_RATE = 20;

    // Synchronzied these
    private List<Item> items;
    private double subTotal;
    private double total;
    private double coupon;
    private int totalWeight;

    public Cart() {
        // Intialize to Arraylist to take advantage of auto sizing
        // and stream
        items = new ArrayList<>();
        subTotal = INITIAL_CART_SUBTOTAL;
        coupon = DEFAULT_COUPON_VALUE;
        totalWeight = INITIAL_CART_WEIGHT;
    }

    /**
     * Sum all subtotal in items
     * @return double
     */
    public double subtotal() {
        // Guard
        if (isEmpty())
            return INITIAL_CART_SUBTOTAL;

        double sumTotal = sumItemPrice();
        sumTotal = coupon != DEFAULT_COUPON_VALUE  ? sumTotal * coupon  : sumTotal;
        setSubTotal(sumTotal);

        return sumTotal;
    }

    /**
     * Sum subTotal with shipping rate if necessary
     * @return double
     */
    public double total() {
        double subTotal = subtotal();
        int sumWeight = sumTotalWeight();
        // Do this to ignore shipping rate for item with zero weight input
        double total = sumWeight > INITIAL_CART_WEIGHT ? subTotal + FLAT_SHIPPING_RATE : subTotal;
        return total;
    }

    public void add(final Item item) {
        synchronized (items) {
            items.add(item);
        }
    }

    public int getQuantityByName(final String name) {
        // count item by item.name
        List<Item> quantity = items.stream().filter((i) -> name.equalsIgnoreCase(i.getName())).collect(Collectors.toList());
        return quantity.size();
    }

    public void applyCoupon(double couponValue) {
         setCoupon((100.00 - couponValue) / 100.00);
    }

    public int size() {
        return items.size();
    }

    private double sumItemPrice() {
        double sumTotal = items.stream().reduce(INITIAL_CART_SUBTOTAL, (sum, i) -> sum += i
                        .getPrice()
                , (sum1, sum2) -> sum1 + sum2 );
        return sumTotal;
    }

    private int sumTotalWeight() {
        int weight = items.stream().reduce(INITIAL_CART_WEIGHT, (sum, i) -> sum += i.getWeight()
                , (sum1, sum2) -> sum1 + sum2 );
        setTotalWeight(weight);
        return weight;
    }

    private synchronized void setCoupon(double value) {
        coupon = value;
    }

    private synchronized void setTotalWeight(int weight) {
        totalWeight = weight;
    }

    private synchronized void setSubTotal(double subTotal) {
        subTotal = subTotal;
    }

    private boolean isEmpty() {
        return items.isEmpty();
    }
}
