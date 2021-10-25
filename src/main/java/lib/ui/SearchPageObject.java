package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, \"Search Wikipedia\")]",
            SEARCH_INPUT = "org.wikipedia:id/search_src_text",
            SEARCH_RESULT_BY_SUBSTRING_DESC_TPL = "//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = '{SUBSTRING}']",
            SEARCH_RESULT_BY_SUBSTRING_TITLE_TPL = "//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '{SUBSTRING}']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "//*[child::*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '{TITLE}'] " +
            "and child::*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = '{DESCRIPTION}}']]",
            SEARCH_CLOSE_BTN = "org.wikipedia:id/search_close_btn",
            SEARCH_ARTICLE_BOOKMARK_ID = "org.wikipedia:id/article_menu_bookmark",
            SEARCH_ADD_TO_LIST_BTN_XPATH = "//*[@text = 'ADD TO LIST']",
            SEARCH_LIST_PLACEHOLDER_ID = "org.wikipedia:id/textinput_placeholder",
            SEARCH_OK_BTN_ID = "android:id/button1",
            SEARCH_TEXT_TPL = "//*[@text = '{SUBSTRING}']",
            SEARCH_VIEW_LIST_XPATH = "//*[@text = 'VIEW LIST']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_DESC_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getElementByText(String title)
    {
        return SEARCH_TEXT_TPL.replace("{SUBSTRING", title);
    }

    private static String getResultSearchElementByTitle(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TITLE_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDesc(String title, String description)
    {
        String modifiedStringWithTitle = SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title);
        return modifiedStringWithTitle.replace("{DESCRIPTION}", description);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element",
                5);

        this.waitForElementPresent(
                By.id(SEARCH_INPUT),
                "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(
                By.id(SEARCH_INPUT), search_line, "Cannot find and type into search input: " + search_line,
                5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Cannot find result with description " + substring,
                15);
    }

    public void waitForSearchResultNotPresent(String substring)
    {
        String search_result_xpath = getResultSearchElementByTitle(substring);
        this.waitForElementNotPresent(
                By.xpath(search_result_xpath),
                "Cannot find result with title " + substring,
                15);
    }

    public void waitForSearchElementAndClick(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "Cannot find result with description " + substring,
                15);
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result_xpath = getResultSearchElementByTitleAndDesc(title, description);
        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Cannot find result with title " + title + " and description " + description,
                15);
    }

    public void checkArticlesSize(int size, String title)
    {
        String articleTitle = SEARCH_RESULT_BY_SUBSTRING_TITLE_TPL.replace("{SUBSTRING}", title);
        List<WebElement> articles = this.waitForElementsPresent(
                By.xpath(articleTitle),
                "Cannot find search results",
                15
        );

        Assert.assertTrue("There are not articles", articles.size() > size);
    }

    public void clickCrossForCancel() {
        this.waitForElementAndClick(
                By.id(SEARCH_CLOSE_BTN),
                "Cannot find search close button",
                15);

        this.waitForElementNotPresent(
                By.id(SEARCH_CLOSE_BTN),
                "found search close button",
                15);
    }
}
