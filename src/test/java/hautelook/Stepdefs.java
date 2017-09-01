package hautelook;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;


public class Stepdefs {
    private Cart cart = new Cart();

    @Given("^I have an empty cart$")
    public void iHaveAnEmptyCart() throws Throwable {
        this.cart = new Cart();
    }

    @Then("^My subtotal should be \"([^\"]*)\" dollars$")
    public void mySubtotalShouldBeDollars(int subtotal) throws Throwable {
        Assert.assertTrue("Total is " + this.cart.subtotal(), this.cart.subtotal() == subtotal);
    }

    @When("^I add a \"([^\"]*)\" dollar item named \"([^\"]*)\"$")
    public void iAddADollarItemNamed(int itemCost, String productName) throws Throwable {
        // Add item to the cart should change the subtotal
        Item one = new Item(productName, itemCost);
        cart.add(one);
    }

    @Given("^I have a cart with a \"([^\"]*)\" dollar item named \"([^\"]*)\"$")
    public void iHaveACartWithADollarItemNamed(int itemCost, String productName) throws Throwable {
        Item firstTee = new Item(productName, itemCost);
        cart.add(firstTee);
    }

    @Then("^My quantity of products named \"([^\"]*)\" should be \"([^\"]*)\"$")
    public void myQuantityOfProductsNamedShouldBe(String productName, int itemCount) throws Throwable {
        Assert.assertTrue("My quantity of products named " + productName + " should be 2", itemCount == cart
                .getQuantityByName(productName));
    }

    @When("^I apply a \"([^\"]*)\" percent coupon code$")
    public void iApplyAPercentCouponCode(int percentOff) throws Throwable {
        cart.applyCoupon(percentOff);
    }

    @When("^I add a \"([^\"]*)\" dollar \"([^\"]*)\" lb item named \"([^\"]*)\"$")
    public void iAddADollarItemWithWeight(int itemCost, int itemWeight, String productName) throws Throwable {
        Item item = new Item(productName, itemCost, itemWeight);
        cart.add(item);
    }

    @Then("^My total should be \"([^\"]*)\" dollars$")
    public void myTotalShouldBeDollars(int total) throws Throwable {
        Assert.assertTrue("Total is " + this.cart.total(), this.cart.total() == total);
    }
}
