package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ElementUtils {
    private final AppiumDriver mobileDriver;
    private final WebDriverWait explicitWait;
    private final Actions gestureAction;

    public ElementUtils(AppiumDriver driver) {
        this.mobileDriver = driver;
        this.explicitWait = new WebDriverWait(driver, 10);
        this.gestureAction = new Actions(driver);
    }

    /**
     * Belirtilen elementin varlığını kontrol ederek döndürür
     */
    public WebElement locateElement(By locator) {
        return waitForPresence(locator);
    }

    /**
     * Belirtilen elementlerin listesini döndürür
     */
    public List<WebElement> locateElements(By locator) {
        return waitForPresenceOfAll(locator);
    }

    /**
     * Belirtilen elemente tıklar
     */
    public void tapElement(By locator) {
        locateElement(locator).click();
    }

    /**
     * Belirtilen elemente metin gönderir
     */
    public void enterText(By locator, String text) {
        locateElement(locator).sendKeys(text);
    }

    /**
     * Elementin içeriğini döndürür
     */
    public String fetchText(By locator) {
        return locateElement(locator).getText();
    }

    /**
     * Elementin içeriğini doğrular
     */
    public boolean verifyElementText(By locator, String expectedText) {
        return explicitWait.until(ExpectedConditions.textMatches(locator, Pattern.compile(expectedText)));
    }

    /**
     * Elementin görünür olup olmadığını doğrular
     */
    public void validateElementVisible(By locator) {
        explicitWait.until(ExpectedConditions.visibilityOf(locateElement(locator)));
    }

    /**
     * Belirtilen elementin varlığını kontrol eder
     */
    public void ensureElementExists(By locator) {
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Sayfanın başlığını doğrular
     */
    public boolean confirmTitle(String expectedTitle) {
        return explicitWait.until(ExpectedConditions.titleIs(expectedTitle));
    }

    /**
     * Elementin belirli bir attribute değerini döndürür
     */
    public String fetchAttribute(By locator, String attribute) {
        return locateElement(locator).getAttribute(attribute);
    }

    /**
     * Elementin belirli bir attribute değerini kontrol eder
     */
    public void validateAttribute(By locator, String attribute, String expectedValue) {
        Assert.assertEquals(fetchAttribute(locator, attribute), expectedValue);
    }

    /**
     * İçeriğinde belirli bir metin olan elemente tıklar
     */
    public void tapElementContainingText(By locator, String text) {
        boolean found = false;
        List<WebElement> elements = locateElements(locator);
        for (WebElement element : elements) {
            if (element.getText().contains(text)) {
                element.click();
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Belirtilen metni içeren element bulunamadı: " + text);
    }

    /**
     * İçeriğinde belirli bir metin olan elementin varlığını kontrol eder
     */
    public void ensureElementWithTextExists(By locator, String text) {
        boolean found = false;
        List<WebElement> elements = locateElements(locator);
        for (WebElement element : elements) {
            if (element.getText().contains(text)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Belirtilen metni içeren element bulunamadı: " + text);
    }

    /**
     * Belirli bir metne sahip elementi bularak ona yeni metin gönderir
     */
    public void enterTextInElementWithText(By locator, String existingText, String newText) {
        boolean found = false;
        List<WebElement> elements = locateElements(locator);
        for (WebElement element : elements) {
            if (element.getText().equals(existingText)) {
                element.sendKeys(newText);
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Belirtilen metne sahip element bulunamadı: " + existingText);
    }

    /**
     * Elementin varlığını bekler
     */
    private WebElement waitForPresence(By locator) {
        return explicitWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Birden fazla elementin varlığını bekler
     */
    private List<WebElement> waitForPresenceOfAll(By locator) {
        return explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * Sayfada belirli bir elemente kaydırma yapar
     */
    public void scrollToElement(WebElement element) {
        String scrollScript = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        ((JavascriptExecutor) mobileDriver).executeScript(scrollScript, element);
    }

    /**
     * Sayfada aşağıya kaydırma yapar
     */
    public void swipeDown(int times) {
        for (int i = 0; i < times; i++) {
            try {
                int screenWidth = mobileDriver.manage().window().getSize().width;
                int screenHeight = mobileDriver.manage().window().getSize().height;
                int startX = screenWidth / 2;
                int startY = (int) (screenHeight * 0.7);
                int endY = (int) (screenHeight * 0.42);
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
                Sequence swipe = new Sequence(finger, 1);
                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                mobileDriver.perform(Collections.singletonList(swipe));
                System.out.println((i + 1) + ". kaydırma işlemi tamamlandı.");
            } catch (Exception e) {
                System.out.println("Kaydırma başarısız: " + e.getMessage());
            }
        }
    }
}
