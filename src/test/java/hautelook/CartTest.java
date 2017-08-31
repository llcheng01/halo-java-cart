package hautelook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CartTest {

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
        Assert.assertTrue(cart.subtotal() == 10.00);
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

        Assert.assertTrue(existingCart.subtotal() == 15.00);
    }

    @Test
    public void applyTenPercentCouponWithCartOfTenDollers() {
        Cart existingCart = new Cart();
        Item shirt = new Item("shirt", 10.00);
        existingCart.add(shirt);
        Assert.assertTrue(existingCart.subtotal() == 10.00);

        // Apply coupon
        existingCart.applyCoupon(10);
        Assert.assertTrue(existingCart.subtotal() == 9.00);
    }

    @Test
    public void addSecondItemToCartAfterApplyingDiscount() {
        // Previous scenario
        Item tank = new Item("tank", 10.00);
        cart.add(tank);
        Assert.assertTrue(cart.subtotal() == 10.00);
        cart.applyCoupon(10);
        Assert.assertTrue(cart.subtotal() == 9.00);

        // Test coupon need to be set only once!!!
        Item dress = new Item("dress", 30);
        cart.add(dress);
        Assert.assertTrue(cart.subtotal() == 36.00);
    }

    @Test
    public void applyShippingFlatRateForItemUnderTenPounds() {
        Item dress = new Item("dress", 78.0, 2);
        Item skirt = new Item("skirt", 20.0, 1);

        cart.add(dress);
        cart.add(skirt);
        Assert.assertTrue(cart.subtotal() == 98.00);

        Assert.assertTrue(cart.total() == 103.00);
    }

}
