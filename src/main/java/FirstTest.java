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
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactroy.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);

        searchPageObject.initSearchInput();
        String substring_article_java = "Object-oriented programming language";
        String title_article_java = "Java (programming language)";
        searchPageObject.typeSearchLine("Java");

        if(Platform.getInstance().isAndroid()) {
            searchPageObject.clickByArticleWithSubstring(substring_article_java);
            articlePageObject.waitForTitleElement(title_article_java);
            String article_title_java = articlePageObject.getArticleTitle(title_article_java);
            String name_of_folder = "Learning Programming";
            articlePageObject.addFirstArticleToMyList(name_of_folder);
            articlePageObject.closeArticle();
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine("Ruby");
            searchPageObject.clickByArticleWithSubstring("Programming language");
            articlePageObject.waitForTitleElement(title_article_java);
            String article_title_ruby = articlePageObject.getArticleTitle(title_article_java);
            articlePageObject.addSecondArticleToMyList(name_of_folder);
            articlePageObject.closeArticle();
            navigationUI.clickMyList();
            myListsPageObject.waitForListPresent(name_of_folder);
            myListsPageObject.openFolderByName(name_of_folder);
            myListsPageObject.swipeByArticleToDelete(article_title_ruby);
            myListsPageObject.waitForArticleToAppearByTitleByXpath(article_title_java);
            myListsPageObject.waitForArticleToDisappearByTitleByXpath(article_title_ruby);
            myListsPageObject.clickByArticleWithTitle(article_title_java);
        }else if(Platform.getInstance().isIOS()){
            String title_and_substring_java = title_article_java + "\n"+ substring_article_java;
            String title_article_ruby = "Ruby (programming language)";
            String substring_article_ruby = "Programming language";
            String title_and_substring_ruby = title_article_ruby + "\n" + substring_article_ruby;
            String substring_article_ruby_in_my_list = "High-level programming language first released in 1995";
            String title_and_substring_ruby_in_my_list = title_article_ruby + "\n" + substring_article_ruby_in_my_list;

            searchPageObject.clickByArticleWithSubstring(title_and_substring_java);
            articlePageObject.waitForTitleElement(title_article_java);
            articlePageObject.addArticleToMyListForIOS();
            articlePageObject.clickByCloseButtonPopoverSyncSavedArticle();
            articlePageObject.closeArticle();
            searchPageObject.initSearchInput();
            searchPageObject.clearSearchField();
            searchPageObject.typeSearchLine("Ruby");
            searchPageObject.clickByArticleWithSubstring(title_and_substring_ruby);
            articlePageObject.waitForTitleElement(title_article_ruby);
            articlePageObject.addArticleToMyListForIOS();
            articlePageObject.closeArticle();
            navigationUI.clickMyList();
            myListsPageObject.swipeByArticleToDelete(title_and_substring_ruby_in_my_list);
            myListsPageObject.waitForArticleToAppearByTitleByXpath(title_and_substring_java);
            myListsPageObject.waitForArticleToDisappearByTitleByXpath(title_and_substring_ruby_in_my_list);
            myListsPageObject.clickByArticleWithTitle(title_and_substring_java);
            articlePageObject.waitForTitleElement(title_article_java);
        } else {
            searchPageObject.clickByArticleWithSubstring(substring_article_java);
            articlePageObject.waitForTitleElement(title_article_java);
            articlePageObject.addArticleToMyListForMW();
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData("rostislover","Aa12345678");
            authorizationPageObject.submitForm();
            articlePageObject.waitForTitleElement(title_article_java);
            assertEquals("We are not on the same page after login.",
                    title_article_java,
                    articlePageObject.getArticleTitle(title_article_java));

            articlePageObject.addArticleToMyListForMW();


            String substring_article_python = "General-purpose, high-level programming language";
            String title_article_python = "Python (programming language)";
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine("Python");
            searchPageObject.clickByArticleWithSubstring(substring_article_python);
            articlePageObject.waitForTitleElement(title_article_python);
            articlePageObject.addArticleToMyListForMW();

            navigationUI.openNavigation();
            navigationUI.clickMyList();
            myListsPageObject.clickByStarToDeleteByArticleTitle(title_article_python);
            myListsPageObject.clickByArticleWithTitle(title_article_java);

        }

        assertEquals(
                "Article is not about '" + title_article_java + "'",
                articlePageObject.getArticleTitle(title_article_java),
                title_article_java
        );
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
