package tests;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
  private SearchPageObject SearchPageObject;
  private ArticlePageObject ArticlePageObject;
  private NavigationUI NavigationUI;
  private MyListsPageObject MyListsPageObject;

  private static final String name_of_folder = "Learning programming";

  @Test
  public void testSaveFirstArticleToMyList() {
    SearchPageObject = SearchPageObjectFactory.get(driver);
    ArticlePageObject = ArticlePageObjectFactory.get(driver);
    NavigationUI = NavigationUIFactory.get(driver);
    MyListsPageObject = MyListsPageObjectFactory.get(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("Object-oriented programming language");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();

    if (Platform.getInstance().isAndroid()) {
      ArticlePageObject.addArticleToMyList(name_of_folder);
    } else {
      ArticlePageObject.addArticlesToMySaved();
      ArticlePageObject.clickOnEmptyPlaceToClosePopup(50, 50);
    }

    ArticlePageObject.closeArticle();
    NavigationUI.clickMyLists();

    if (Platform.getInstance().isAndroid()) {
      MyListsPageObject.openFolderByName(name_of_folder);
    }

    MyListsPageObject.swipeByArticleToDelete(article_title);
  }
}
