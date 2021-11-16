import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class SearchTest extends CoreTestCase {

    protected MainPageObject mainPageObject;

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();

        mainPageObject = new MainPageObject(driver);
    }

    @Test
    @DisplayName("Check Text Hint")
    @Description("Check that text has a hint")
    @Feature("Positive test cases")
    @Issue("159781")
    @Severity("Minor")
    @Story("User story")
    public void testHintTextTest() throws InterruptedException
    {
        mainPageObject.assertElementHasText(By.xpath("//*[@resource-id = 'org.wikipedia:id/search_container']//*[@text = 'Search Wikipedia']"),
                "Search Wikipedia", "Search input doesn't contain 'Search Wikipedia'");
    }

    // Ex 9*
    @Test
    @DisplayName("Every Result Contains Search Word")
    @Description("Check Every Result Contains Search Word after pushing button search")
    @Feature("Positive test cases")
    @Issue("159782")
    @Severity("CRITICAL")
    @Story("User story")
    public void testEveryResultContainsSearchWord()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        // Находим и кликаем по полю ввода
        searchPageObject.initSearchInput();

        // Вводим поисковое слово Ford
        searchPageObject.typeSearchLine("Java");

        // Проверяем, что есть хотя бы одно слово в поиске
        searchPageObject.waitForElementByTitleAndDescription("Java", "Object-oriented programming language");

        // Убеждаемся, что нашлось больше одной статьи (проверяют заголовки результатов)
        searchPageObject.checkArticlesSize(3, "Java");
    }

    // Ex 3
    @Test
    @DisplayName("Check cancel search")
    @Description("Check cancel search after enter search word in search input")
    @Feature("Positive test cases")
    @Issue("159783")
    @Severity("BLOCKER")
    @Story("User story")
    public void testCancelSearch() throws InterruptedException {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        // Находим и кликаем по полю ввода
        searchPageObject.initSearchInput();

        // Вводим поисковое слово Ford
        searchPageObject.typeSearchLine("Ford");

        // Проверяем, что есть хотя бы одно слово в поиске
        searchPageObject.waitForSearchResult("Ford");

        // Убеждаемся, что нашлось больше одной статьи
        searchPageObject.checkArticlesSize(1, "Ford");

        // Жмакаем на крестик, чтобы отменить поиск
        searchPageObject.clickCrossForCancel();

        // Проверяем, что результаты поиска отсутствуют
        searchPageObject.waitForSearchResultNotPresent("Ford");
    }
}
