package executer;

import io.appium.java_client.AppiumDriver;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import util.ManageDriver;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"stepImplementations", "core"},
        tags = "@scenario2",
        plugin = {
                "summary",
                "pretty",
                "html:output/CucumberReport/report.html",
                "json:output/CucumberReport/report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class TestExecutor extends AbstractTestNGCucumberTests {
    static AppiumDriver driver = ManageDriver.getDriver();
}
