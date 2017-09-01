package hautelook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO need to be easily Extendable
// Internationalization?
public class Cart {
    private static final double INITIAL_CART_SUBTOTAL = 0.0;
    private static final double DEFAULT_COUPON_VALUE = 0.0;
    private static final int INITIAL_CART_WEIGHT = 0;
    private static final int FLAT_SHIPPING_RATE = 5;
    private static final double STANDARD_SHIPPING_RATE = 20.00;
    private static final double FREE_SHIPPING_AMOUNT = 100.00;

    // Synchronzied these
    private List<Item> items;
    private double coupon;

    public Cart() {
        // Intialize to Arraylist to take advantage of auto sizing
        // and stream
        items = new ArrayList<>();
        coupon = DEFAULT_COUPON_VALUE;
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

        return sumTotal;
    }

    /**
     * Sum subTotal with shipping rate if necessary
     * @return double
     */
    public double total() {
        // 1. if freeShipping is true, all items are under 10 lbs AND subtotal is OVER $100.00
        // 2. if freeShipping is false
        // 3. all items is under 10 lbs, subtotal is under 100.00, flat rate
        // 4. all items is not under 10 lbs, shipping rate is standard per item

        final double subTotal = subtotal();
        final int count = countOverWeightItems();

        final boolean freeShipping = isFreeShipping(subTotal, count);
        if (freeShipping)
            return subTotal;

        final boolean flatRate  = isFlateRateShipping(subTotal, count);

        double shippingCost = 0;
        if (flatRate)
            shippingCost += FLAT_SHIPPING_RATE;

        shippingCost += standardShippingCost(count);

        final double total = subTotal + shippingCost;
        return total;
    }

    public void add(final Item item) {
        synchronized (items) {
            items.add(item);
        }
    }

    public int getQuantityByName(final String name) {
        if (items.isEmpty())
            return items.size();
        // count item by item.name
        List<Item> quantity = items.stream().filter((i) -> name.equalsIgnoreCase(i.getName())).collect(Collectors.toList());
        return quantity.size();
    }

    public void applyCoupon(double couponValue) {
         setCoupon((100.00 - couponValue) / 100.00);
    }

    private double sumItemPrice() {
        if (items.isEmpty())
            return INITIAL_CART_SUBTOTAL;

        double sumTotal = items.stream().reduce(INITIAL_CART_SUBTOTAL, (sum, i) -> sum += i
                        .getPrice()
                , (sum1, sum2) -> sum1 + sum2 );
        return sumTotal;
    }

    private int sumTotalWeight() {
        if (items.isEmpty())
            return 0;

        int weight = items.stream().reduce(INITIAL_CART_WEIGHT, (sum, i) -> sum += i.getWeight()
                , (sum1, sum2) -> sum1 + sum2 );
        return weight;
    }

    // Find items over 10 pounds and multiple standard shipping cost
    private int countOverWeightItems() {
        if (items.isEmpty())
            return 0;
        // for each item over 10 lbs add 20 sum
        List<Item> overWeightItems = items.stream().filter((i) -> i.getWeight() >= 10).collect
                (Collectors.toList());

        return overWeightItems.size();
    }

    private double standardShippingCost(final int overWeightItemsCount) {
        final double shippingCost = overWeightItemsCount == 0.0 ? 0.0 : overWeightItemsCount *
                STANDARD_SHIPPING_RATE;
        return shippingCost;
    }

    private boolean isFreeShipping(final double subTotal, final int overWeightItemsCount) {
        // order is $100 or more, and each individual item is under 10 lb
        final boolean free = (subTotal > FREE_SHIPPING_AMOUNT && overWeightItemsCount == 0);
        return free;
    }

    private boolean isFlateRateShipping(final double subTotal, final int overWeightItemsCount) {
        // order is under $100, and all items are 10 lb or more
        final boolean flateRate = (subTotal < FREE_SHIPPING_AMOUNT && overWeightItemsCount !=
                items.size());
        return flateRate;
    }

    private synchronized void setCoupon(double value) {
        coupon = value;
    }


    private boolean isEmpty() {
        return items.isEmpty();
    }
}
