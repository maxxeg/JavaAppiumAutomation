package tests;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
  private SearchPageObject SearchPageObject;
  private ArticlePageObject ArticlePageObject;
  private NavigationUI NavigationUI;
  private MyListsPageObject MyListsPageObject;

  @Test
  public void testSaveFirstArticleToMyList() {
    SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject = new ArticlePageObject(driver);
    NavigationUI = new NavigationUI(driver);
    MyListsPageObject = new MyListsPageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.waitForSearchResult("Object-oriented programming language");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();
    String name_of_folder = "Learning programming";
    ArticlePageObject.addArticleToMyList(name_of_folder);
    ArticlePageObject.closeArticle();
    NavigationUI.clickMyLists();
    MyListsPageObject.openFolderByName(name_of_folder);
    MyListsPageObject.swipeByArticleToDelete(article_title);
  }
}
