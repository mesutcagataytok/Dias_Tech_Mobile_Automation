package util;


import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import org.testng.Reporter;

import java.util.Properties;

public class Hooks {

     AppiumDriver driver;
     Properties properties;
    @Before
    public void before() {
        String browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
        properties = ConfigReader.initialize_Properties();
        driver = ManageDriver.initialize_Driver(browser);
        if (driver == null) {
            throw new RuntimeException("Driver is NULL, checkout !!!");
        }
    }

    @After
    public void after() {
        if (driver != null) {
            System.out.println("Quit Appium Driver");
            driver.quit();
        } else {
            System.out.println("Driver is NULL, checkout !!!");
        }
    }
}
