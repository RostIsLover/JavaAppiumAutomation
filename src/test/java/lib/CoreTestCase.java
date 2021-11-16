package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import lib.ui.MainPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String appiumURL = "http://127.0.0.1:4723/wd/hub";

    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        //capabilities.setCapability("platformVersion", "10");
        //capabilities.setCapability("platformVersion", "Android 10");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        //capabilities.setCapability("app", "D:\\java_projects\\sources\\JavaAppiumAutomation\\apks\\org.wikipedia_50374_apps.evozi.com.apk");
        capabilities.setCapability("app", "C:\\Users\\rpic\\IdeaProjects\\JavaAppiumAutomation\\apks\\org.wikipedia_50374_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL(appiumURL), capabilities);

        new MainPageObject(driver).waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find search input",
                15
        );

        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void tearDown() throws Exception
    {
        driver.quit();

        super.tearDown();
    }
}
