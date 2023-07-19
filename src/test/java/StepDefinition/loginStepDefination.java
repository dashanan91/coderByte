package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class loginStepDefination extends main.java.basicFunctionalities {

    WebDriver driver = null;
    Map<String, String> loginCredentials = new HashMap<>();

    Map<String, String> locators = new HashMap<>();

    @Given("User loads driver to run test on {string} browser")
    public void user_loads_driver_to_run_test_on_browser(String browserName) throws IOException {
        switch(browserName.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        locators = gettingValuesFromProperties("locator.properties");
    }

    @Given("User loads landing page of school webpage taking url from {string}")
    public void user_loads_landing_page_of_school_webpage_taking_url_from(String propertiesFileName) throws IOException {
        loginCredentials = gettingValuesFromProperties("credentials/loginCreds.properties");
       driver.get(loginCredentials.get("url"));

    }
    @When("User enters login credentials taking data from {string}")
    public void user_enters_login_credentials_taking_data_from(String propertiesFileName) {
        driver.findElement(
                By.xpath(locators.get("userNameTextBox"))
        ).sendKeys(
                loginCredentials.get("userName"));

        driver.findElement(
                By.xpath(locators.get("passwordTextBox"))
        ).sendKeys(
                loginCredentials.get("password"));

        driver.findElement(By.xpath(locators.get("loginButton"))).click();
    }
    @Then("User should login to the landing page verified from logged in user as {string}")
    public void user_should_login_to_the_landing_page_verified_from_logged_in_user_as(String expectedUserName) {
        String nameFromUI = driver.findElement(By.xpath(locators.get("loggedInUserName"))).getText();
        Assert.assertEquals(nameFromUI, expectedUserName, "logged in username do not match");
    }
    @When("User clicks on StaffProfileManagement dropdown and clicks on StaffAdd page")
    public void user_clicks_on_staff_profile_management_dropdown_and_clicks_on_staff_add_page() throws InterruptedException {

        waitingForLoaderToDisappear(driver);
        WebElement staffProfileManagement = new WebDriverWait(
                driver,Duration.ofSeconds(10)).until(
                        ExpectedConditions.elementToBeClickable(By.xpath(
                                locators.get("staffProfileManagementDashboardSubMenuItem"))));

        staffProfileManagement.click();
        driver.findElement(By.xpath(locators.get("staffAddSubMenuIcon"))).click();
    }
    @Then("User should land on a page requesting details of staff to be added")
    public void user_should_land_on_a_page_requesting_details_of_staff_to_be_added() {
        String pageName = driver.findElement(By.xpath(locators.get("personalDetailsPageNameLocator"))).getText();
        Assert.assertEquals(pageName, "Personal Details", "personal details page not opened");
    }

    @Then("User fills login details from excel file {string}")
    public void user_fills_login_details_from_excel_file(String string) {

        Map<String, String> valuesFromExcel = readingValueFromExcel(string);
        System.out.println("values from xl is as " + valuesFromExcel);

        driver.findElement(By.xpath(locators.get("fullNameTextBoxPersonalDetails"))).sendKeys(valuesFromExcel.get("Fullname"));


        dateSelector(driver, "dob", valuesFromExcel.get("DOB"));

        dateSelector(driver, "joinDate",valuesFromExcel.get("DOJ"));

        String generatedString = RandomStringUtils.randomAlphanumeric(10);
        driver.findElement(By.xpath(locators.get("emailIdTextBox"))).sendKeys(generatedString + valuesFromExcel.get("EmailId"));

        dateSelector(driver, "salaryStartDate",valuesFromExcel.get("salaryStartDate"));

        driver.findElement(By.xpath(locators.get("personalDetailsSubmitButton"))).click();
    }



    @Then("User clicks on FeeConfigurationMenu and verifies {string} as heading of page")
    public void user_clicks_on_fee_configuration_menu_and_verifies_as_heading_of_page(String string) throws InterruptedException {

        driver.findElement(By.xpath(locators.get("dashboardMenuItem"))).click();
        Thread.sleep(5000);

        WebElement FeeConfigurationPage = new WebDriverWait(driver,Duration.ofSeconds(10)).until(
                ExpectedConditions.elementToBeClickable(By.xpath(locators.get("feeConfigurationMenuItem"))));
        FeeConfigurationPage.click();
    }

    @When("User enters {string} into GlocbalStudentSearch box and searches for student")
    public void user_enters_into_glocbal_student_search_box_and_searches_for_student(String studentId) throws InterruptedException {
        WebElement globalStudentSearchBar = driver.findElement(By.xpath(locators.get("globalStudentSearchBar")));
        globalStudentSearchBar.sendKeys(studentId);
        globalStudentSearchBar.sendKeys(Keys.ENTER);
        waitingForLoaderToDisappear(driver);

    }


    @Then("User should find one pending Invoice section from which user chooses payment mode as {string} under payments")
    public void user_should_find_one_pending_invoice_section_from_which_user_chooses_payment_mode_as_under_payments(String string) {
        driver.findElement(By.xpath(locators.get("unInvoicedComponents"))).click();
        driver.findElement(By.xpath(locators.get("firstUnpaidComponentCheckBox"))).click();


        JavascriptExecutor je = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.xpath(locators.get("paymentModeDropDown")));
        je.executeScript("arguments[0].scrollIntoView(true);",element);

        element.click();
        driver.findElement(By.xpath(locators.get("paymentModeCash"))).click();
    }
    @Then("User clicks on pay button to collect the fee")
    public void user_clicks_on_pay_button_to_collect_the_fee() {
        driver.findElement(By.xpath(locators.get("payButton"))).click();
    }
    @Then("User verifies that payment has been completed successfully")
    public void user_verifies_that_payment_has_been_completed_successfully() {

    }

}
