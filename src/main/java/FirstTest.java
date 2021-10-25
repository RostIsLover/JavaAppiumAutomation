import lib.CoreTestCase;
import lib.ui.ArticlesPageObject;
import lib.ui.MainPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class FirstTest extends CoreTestCase {

    protected MainPageObject mainPageObject;
    protected SearchPageObject searchPageObject;
    protected NavigationUI navigationUI;
    protected ArticlesPageObject articlesPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        mainPageObject = new MainPageObject(driver);
        searchPageObject = new SearchPageObject(driver);
        navigationUI = new NavigationUI(driver);
        articlesPageObject = new ArticlesPageObject(driver);

    }

    @Test
    public void testFirstTest() throws InterruptedException
    {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    // Ex 5
    @Test
    public void testSaveTwoArticles()
    {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchElementAndClick("Object-oriented programming language");
        navigationUI.createAndAddToMyList("My List");
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Python");
        searchPageObject.waitForSearchElementAndClick("General-purpose, high-level programming language");
        navigationUI.addToListAndViewList("My List");
        articlesPageObject.deleteItemFromList("Python (programming language");
    }

    //Ex 6
    @Test
    public void testAssertTitle()
    {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchElementAndClick("Object-oriented programming language");
        mainPageObject.assertElementPresent("title");
    }


    @Test
    public void testCancelSearch()
    {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickCrossForCancel();
    }
}
