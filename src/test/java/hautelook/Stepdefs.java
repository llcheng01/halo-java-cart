package hautelook;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;


public class Stepdefs {
    private Cart cart = new Cart();
    // private Cart existingCart = new Cart();

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
        // Assert.assertEquals(1, cart.size());
        Assert.assertTrue("Total is " + this.cart.subtotal(), this.cart.subtotal() == itemCost);
    }

    @Given("^I have a cart with a \"([^\"]*)\" dollar item named \"([^\"]*)\"$")
    public void iHaveACartWithADollarItemNamed(int itemCost, String productName) throws Throwable {
        Item firstTee = new Item(productName, itemCost);
        cart.add(firstTee);
    }

    @Then("^My quantity of products named \"([^\"]*)\" should be \"([^\"]*)\"$")
    public void myQuantityOfProductsNamedShouldBe(String productName, int itemCount) throws Throwable {

//        Item secondTee = new Item(productName, 5.00);
//        cart.add(secondTee);
////        Assert.assertTrue("My quantity of products named " + productName + " should be 2", 2 == cart
////                .getQuantityByName(productName));
//
//        Item jewelry1 = new Item("jewelry", 100.00);
//        Item jewelry2 = new Item("jewelry", 100.00);
//        Item belt = new Item("belt", 5.00);
//
//        cart.add(jewelry1);
//        cart.add(belt);
//        cart.add(jewelry2);

        Assert.assertTrue("My quantity of products named " + productName + " should be 2", itemCount == cart
                .getQuantityByName(productName));
    }

    @When("^I apply a \"([^\"]*)\" percent coupon code$")
    public void iApplyAPercentCouponCode(int percentOff) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I add a \"([^\"]*)\" dollar \"([^\"]*)\" lb item named \"([^\"]*)\"$")
    public void iAddADollarItemWithWeight(int itemCost, int itemWeight, String productName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^My total should be \"([^\"]*)\" dollars$")
    public void myTotalShouldBeDollars(int total) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
