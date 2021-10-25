package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ArticlesPageObject extends MainPageObject {

    private static final String
            SEARCH_TEXT_TPL = "//*[@text = '{SUBSTRING}']";

    public ArticlesPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getElementByText(String title)
    {
        return SEARCH_TEXT_TPL.replace("{SUBSTRING", title);
    }
    /* TEMPLATES METHODS */

    public void deleteItemFromList(String itemName)
    {
        String elementForDeleteByItemName = getElementByText(itemName);
        this.swipeElementToLeft(
                By.xpath(elementForDeleteByItemName),
                "Error: swipe to delete article failed"
        );

        this.waitForElementNotPresent(
                By.xpath(elementForDeleteByItemName),
                "Looks like removing article is still in list",
                15
        );
    }
}
