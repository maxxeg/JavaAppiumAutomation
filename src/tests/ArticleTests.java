package tests;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase
{
  private SearchPageObject SearchPageObject;
  private ArticlePageObject ArticlePageObject;

  @Test
  public void testCompareArticleTitle() {
    SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject = new ArticlePageObject(driver);

    SearchPageObject.initSearchInput();
    String placeholder = SearchPageObject.getSearchPlaceholder();

    assertEquals(
            "Not present search placeholder",
            "Search…",
            placeholder
    );

    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    String article_title = ArticlePageObject.getArticleTitle();

    assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            article_title
    );
  }

  @Test
  public void testSwipeArticle() {
    SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject = new ArticlePageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Appium");
    SearchPageObject.waitForSearchResult("Appium");
    SearchPageObject.clickByArticleWithSubstring("Appium");
    ArticlePageObject.waitForTitleElement();
    ArticlePageObject.swipeToFooter();
  }

}
