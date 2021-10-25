package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject {

    private static final String
            SEARCH_RESULT_BY_SUBSTRING_DESC_TPL = "//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = '{SUBSTRING}']",
            SEARCH_RESULT_BY_SUBSTRING_TITLE_TPL = "//*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '{SUBSTRING}']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "//*[child::*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = '{TITLE}'] " +
                    "and child::*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text = '{DESCRIPTION}}']]",
            SEARCH_ARTICLE_BOOKMARK_ID = "org.wikipedia:id/article_menu_bookmark",
            SEARCH_ADD_TO_LIST_BTN_XPATH = "//*[@text = 'ADD TO LIST']",
            SEARCH_LIST_PLACEHOLDER_ID = "org.wikipedia:id/textinput_placeholder",
            SEARCH_OK_BTN_ID = "android:id/button1",
            SEARCH_TEXT_TPL = "//*[@text = '{SUBSTRING}']",
            SEARCH_VIEW_LIST_XPATH = "//*[@text = 'VIEW LIST']";


    public NavigationUI(AppiumDriver driver) {
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

    public void createAndAddToMyList(String listName)
    {
        this.waitForElementAndClick(
                By.id(SEARCH_ARTICLE_BOOKMARK_ID),
                "Cannot find button to save the article",
                15
        );

        this.waitForElementAndClick(
                By.xpath(SEARCH_ADD_TO_LIST_BTN_XPATH),
                "Cannot find option 'Add to reading list'",
                15
        );

        this.waitForElementAndSendKeys(
                By.id(SEARCH_LIST_PLACEHOLDER_ID),
                listName,
                "Cannot find this list name input", 15
        );

        this.waitForElementAndClick(
                By.id(SEARCH_OK_BTN_ID),
                "Cannot find OK button",
                15
        );
    }

    public void addToListAndViewList(String listName)
    {
        this.waitForElementAndClick(
                By.id(SEARCH_ARTICLE_BOOKMARK_ID),
                "Cannot find button to save the article",
                15
        );

        this.waitForElementAndClick(
                By.xpath(SEARCH_ADD_TO_LIST_BTN_XPATH),
                "Cannot find option 'Add to reading list'",
                15
        );

        String listXpath = getElementByText(listName);
        this.waitForElementAndClick(
                By.xpath(listXpath),
                "Cannot find" +  listXpath,
                15
        );

        this.waitForElementAndClick(
                By.xpath(SEARCH_VIEW_LIST_XPATH),
                "Cannot find button to view list",
                15
        );
    }
}
