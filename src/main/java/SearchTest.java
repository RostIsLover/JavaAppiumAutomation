import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchTest {

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
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void hintTextTest() throws InterruptedException
    {
        assertElementHasText(By.xpath("//*[@resource-id = 'org.wikipedia:id/search_container']//*[@text = 'Search Wikipedia']"),
                "Search Wikipedia", "Search input doesn't contain 'Search Wikipedia'");
    }

    @Test
    public void everyResultContainsSearchWord()
    {
        // Находим и климкаем по полю ввода
        waitForElementAndClick(
                By.xpath("//*[contains(@text, \"Search Wikipedia\")]"),
                "Cannot find search input",
                15
        );

        // Вводим поисковое слово Java
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search line", 15
        );

        // Проверяем, что есть хотя бы одно слово в поиске
        waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java')]"),
                "There is at least one result",
                15
        );

        // Считаем, сколько статей нашлось
        List<WebElement> articles = waitForElementsPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Java')]"),
                "Cannot find search results",
                15
        );
        System.out.println("Количество результатов, где содержится поисковое слово Java: " + articles.size());
    }

    @Test
    public void testCancelSearch() throws InterruptedException {

        // Находим и климкаем по полю ввода
        waitForElementAndClick(
                By.xpath("//*[contains(@text, \"Search Wikipedia\")]"),
                "Cannot find search input",
                15
        );

        // Вводим поисковое слово Ford
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Ford",
                "Cannot find search line", 15
        );

        // Проверяем, что есть хотя бы одно слово в поиске
        waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Ford')]"),
                "There is at least one result",
                15
        );

        // Считаем, сколько статей нашлось
        List<WebElement> fordArticles = waitForElementsPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Ford')]"),
                "Cannot find search results",
                15
        );

        // Убеждаемся, что нашлось больше одной статьи
        Assert.assertTrue("There are not articles", fordArticles.size() > 1);

        // Жмакаем на крестик, чтобы отменить поиск
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find search close button",
                15
        );

        // Проверяем, что результаты поиска отсутствуют
        waitForElementNotPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][contains(@text, 'Ford')]"),
                "Found search results - still stay at search results",
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

    private List<WebElement> waitForElementsPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }


    private void assertElementHasText(By by, String expected_text, String error_message)
    {
        WebElement element = waitForElementPresent(by, "assertElementHasText: Element is not found");
        Assert.assertEquals(
                error_message,
                expected_text,
                element.getText()
        );
    }
}
