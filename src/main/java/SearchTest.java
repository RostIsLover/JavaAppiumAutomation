import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class SearchTest extends CoreTestCase {

    protected MainPageObject mainPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testHintTextTest() throws InterruptedException
    {
        mainPageObject.assertElementHasText(By.xpath("//*[@resource-id = 'org.wikipedia:id/search_container']//*[@text = 'Search Wikipedia']"),
                "Search Wikipedia", "Search input doesn't contain 'Search Wikipedia'");
    }

    @Test
    public void testEveryResultContainsSearchWord()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        // Находим и кликаем по полю ввода
        searchPageObject.initSearchInput();

        // Вводим поисковое слово Ford
        searchPageObject.typeSearchLine("Ford");

        // Проверяем, что есть хотя бы одно слово в поиске
        searchPageObject.waitForSearchResult("Ford");

        // Убеждаемся, что нашлось больше одной статьи
        searchPageObject.checkArticlesSize(1, "Ford");
    }

    // Ex 3
    @Test
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
