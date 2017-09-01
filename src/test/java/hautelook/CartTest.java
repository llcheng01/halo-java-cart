package hautelook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartTest {
    private static final double DELTA = 0.1;

    private Cart cart;

    @Before
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void emptyCartSubtotal() {
        Assert.assertTrue(cart.subtotal() == 0);
    }

    @Test
    public void addTenToEmptyCart() {
        Item shirt = new Item("shirt", 10.00);
        cart.add(shirt);
        Assert.assertEquals(10.00, cart.subtotal(), DELTA);
    }

    @Test
    public void addItemAlreadyExistingInTheCart() {
        Cart existingCart = new Cart();
        Item tee1 = new Item("tee", 5.00);
        existingCart.add(tee1);

        Item tee2 = new Item("tee", 5.00);
        existingCart.add(tee2);
        Assert.assertEquals(2, existingCart.getQuantityByName("tee"));
    }

    @Test
    public void addItemTwiceShouldShowQuantityOfTwo() {
        Item jewelry1 = new Item("jewelry", 100.00);
        Item jewelry2 = new Item("jewelry", 100.00);
        Item belt = new Item("belt", 5.00);

        cart.add(jewelry1);
        cart.add(belt);
        cart.add(jewelry2);
        Assert.assertEquals(2, cart.getQuantityByName("jewelry"));
    }

    @Test
    public void addTenDollarItemWithExistingCartWithFiveDollarItem() {
        Cart existingCart = new Cart();
        Item tee1 = new Item("tee", 5.00);
        existingCart.add(tee1);

        Item shirt = new Item("shirt", 10.00);
        existingCart.add(shirt);

        Assert.assertEquals(15.00, existingCart.subtotal(), DELTA);
    }

    @Test
    public void applyTenPercentCouponWithCartOfTenDollers() {
        Cart existingCart = new Cart();
        Item shirt = new Item("shirt", 10.00);
        existingCart.add(shirt);
        Assert.assertEquals(10.00, existingCart.subtotal(), DELTA);

        // Apply coupon
        existingCart.applyCoupon(10);
        Assert.assertEquals(9.00, existingCart.subtotal(), DELTA);
    }

    @Test
    public void addSecondItemToCartAfterApplyingDiscount() {
        // Previous scenario
        Item tank = new Item("tank", 10.00);
        cart.add(tank);
        Assert.assertEquals(10.00, cart.subtotal(), DELTA);
        cart.applyCoupon(10);
        Assert.assertEquals(9.00, cart.subtotal(), DELTA);

        // Test coupon need to be set only once!!!
        Item dress = new Item("dress", 30);
        cart.add(dress);
        Assert.assertEquals(36.00, cart.subtotal(), DELTA);
    }

    @Test
    public void applyShippingFlatRateForItemUnderTenPounds() {
        Item dress = new Item("dress", 78.0, 2);
        Item skirt = new Item("skirt", 20.0, 1);

        cart.add(dress);
        cart.add(skirt);
        Assert.assertEquals(98.00, cart.subtotal(), DELTA);

        Assert.assertEquals(103.00, cart.total(), DELTA);
    }

    @Test
    public void applyPerItemShippingForItemOverTenPoundsWithoutFlatRate() {
        Item dresser = new Item("dresser", 49.00, 50);
        Item indoorRug = new Item("indoor rug", 49.00, 50);

        cart.add(dresser);
        cart.add(indoorRug);

        Assert.assertEquals(98.0, cart.subtotal(), DELTA);
        Assert.assertEquals(138.00, cart.total(), DELTA);
    }

    @Test
    public void shippingWillbeFreeWhenItemsOver100AndItemsNotOverWeighted() {
        Item dress = new Item("dress", 68.00, 2);
        Item skirt1 = new Item("skirt", 20.00, 1);
        Item skirt2 = new Item("skirt", 20.00, 1);

        cart.add(dress);
        cart.add(skirt1);
        cart.add(skirt2);

        Assert.assertEquals(108.0, cart.subtotal(), DELTA);
        Assert.assertEquals(108.00, cart.total(), DELTA);
        Assert.assertEquals(2, cart.getQuantityByName("skirt"));
    }

    @Test
    public void applyStandardShippingRateForAllItemsOverTenPoundsWithoutFlatRate() {
        Item dress = new Item("dress", 80.00, 2);
        Item tee = new Item("tee", 10.00, 2);
        Item couch = new Item("couch", 50.00, 100);

        cart.add(dress);
        cart.add(tee);
        cart.add(couch);

        Assert.assertEquals(140.00, cart.subtotal(), DELTA);
        Assert.assertEquals(160.00, cart.total(), DELTA);
    }

    @Test
    public void orderUnder100WillHasFlatRateAndStandardShippingRatePerItem() {
        Item tee = new Item("tee", 10.00, 1);
        Item lamp = new Item("lamp", 25.00, 15);
        Item endTable = new Item("end table", 50.00, 25);

        cart.add(tee);
        cart.add(lamp);
        cart.add(endTable);

        Assert.assertEquals(85.00, cart.subtotal(), DELTA);
        Assert.assertEquals(130.0, cart.total(), DELTA);
    }
}
