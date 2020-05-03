package tests;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase
{
  private SearchPageObject SearchPageObject;
  private ArticlePageObject ArticlePageObject;

  @Test
  public void testCompareArticleTitle() {
    SearchPageObject = SearchPageObjectFactory.get(driver);
    ArticlePageObject = ArticlePageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
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
    SearchPageObject = SearchPageObjectFactory.get(driver);
    ArticlePageObject = ArticlePageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    ArticlePageObject.waitForTitleElement();
    ArticlePageObject.swipeToFooter();
  }

}
