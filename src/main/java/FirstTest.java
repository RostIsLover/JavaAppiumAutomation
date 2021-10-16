import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:\\java_projects\\sources\\JavaAppiumAutomation\\apks\\org.wikipedia_50374_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find search input",
                15
        );

        if (driver.getOrientation() == ScreenOrientation.LANDSCAPE)
        {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest() throws InterruptedException
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, \"Search Wikipedia\")]"),
                "Cannot find search input",
                15
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search line", 15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = 'Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' in topic by searching 'Java'",
                15
        );
    }

    @Test
    public void saveTwoArticles()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                15
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search line", 15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = 'Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' in topic by searching 'Java'",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/article_menu_bookmark"),
                "Cannot find button to save the article",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'ADD TO LIST']"),
                "Cannot find option 'Add to reading list'",
                15
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/textinput_placeholder"),
                "My List",
                "Cannot find this list name input", 15
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot find OK button",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                15
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Python",
                "Cannot find search line", 15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = 'General-purpose, high-level programming language']"),
                "Cannot find 'General-purpose, high-level programming language' in topic by searching 'Python'",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/article_menu_bookmark"),
                "Cannot find button to save the article",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'ADD TO LIST']"),
                "Cannot find option 'Add to reading list'",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'My List']"),
                "Cannot find My List",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'VIEW LIST']"),
                "Cannot find button to view list",
                15
        );

        swipeElementToLeft(
                By.xpath("//*[@text = 'General-purpose, high-level programming language']"),
                "Error: swipe to delete python article failed"
        );

        waitForElementPresent(
                By.xpath("//*[@text = 'Python (programming language) removed from My List']"),
                "Looks like Python article is still in list",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'Java (programming language)']"),
                "Cannot find Java article to click",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@text = 'Java (programming language)']"),
                "Cannot find Title Java",
                15
        );
    }

    @Test
    public void assertTitle()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                15
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search line", 15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = 'Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' in topic by searching 'Java'",
                15
        );

        assertElementPresent("title");
    }


    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, \"Search Wikipedia\")]"),
                "Cannot find search input",
                15
        );

        waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Cannot find search line", 15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find search close button",
                15
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "found search close button",
                15
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message,  long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message,  long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);

        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(150)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release()
                .perform();
    }

    private void assertElementPresent(String element)
    {
        Assert.assertTrue("Element " + element + " doesn't exist", driver.findElements(By.xpath(element)).size() != 0);
    }
}
