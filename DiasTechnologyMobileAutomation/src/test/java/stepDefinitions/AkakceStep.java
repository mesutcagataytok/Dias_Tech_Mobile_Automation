package stepDefinitions;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AkakcePage;
import util.ManageDriver;

public class AkakceSteps {

    AppiumDriver driver = ManageDriver.getDriver();
    private AkakcePage akakcePage;

    public AkakceSteps() {
        this.driver = ManageDriver.getDriver();
        this.akakcePage = new AkakcePage(driver);
    }

    @Given("User opens the Akakce mobile app")
    public void userOpensAkakceApp() {
        akakcePage.launchTheApp();
    }

    @When("User skips the login if prompted with a sign-in screen")
    public void userSkipsLoginIfPrompted() {
        akakcePage.continueWithoutLogin();
    }

    @And("User types {string} into the search field and starts the search")
    public void userTypesSearchQuery(String searchQuery) {
        akakcePage.Search(searchQuery);
    }

    @And("User taps the filtering option")
    public void userTapsFilterOption() {
        akakcePage.Filter();
    }

    @And("User navigates to Computers → Components section and selects View Items")
    public void userNavigatesToComputerComponents() {
        akakcePage.sortFilter();
    }

    @And("User arranges the items in descending price order")
    public void userSortsByHighestPrice() {
        akakcePage.highprice();
    }

    @And("User selects the 10th product from the displayed results")
    public void userSelectsTenthProduct() {
        akakcePage.tentProduct();
    }

    @And("User presses the Visit Product button")
    public void userPressesVisitProduct() {
        akakcePage.goProduct();
    }

    @Then("User should confirm the presence of the Go to Seller button on the product details page")
    public void userConfirmsGoToSellerButton() {
        akakcePage.goSellerBttnCheck();
    }
}
