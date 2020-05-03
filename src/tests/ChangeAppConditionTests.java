package tests;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase
{
  private SearchPageObject SearchPageObject;
  private ArticlePageObject ArticlePageObject;

  @Test
  public void testChangeScreenOrientationOnSearchResults()   {
    SearchPageObject = SearchPageObjectFactory.get(driver);
    ArticlePageObject = ArticlePageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    String title_before_rotation = ArticlePageObject.getArticleTitle();
    this.rotateScreenLandscape();
    String title_after_rotation = ArticlePageObject.getArticleTitle();

    assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_rotation
    );

    this.rotateScreenPortrait();
    String title_after_second_rotation = ArticlePageObject.getArticleTitle();

    assertEquals(
            "Article title have been changed after screen rotation",
            title_after_rotation,
            title_after_second_rotation
    );
  }

  @Test
  public void testCheckSearchArticleInBackground()   {
    SearchPageObject = SearchPageObjectFactory.get(driver);
    ArticlePageObject = ArticlePageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("Object-oriented programming language");
    this.backgroundApp(2);
    SearchPageObject.waitForSearchResult("Object-oriented programming language");
  }
}
