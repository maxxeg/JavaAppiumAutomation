package tests;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import java.util.List;

public class HomeWork extends CoreTestCase
{
  private SearchPageObject SearchPageObject;
  private ArticlePageObject ArticlePageObject;
  private NavigationUI NavigationUI;
  private MyListsPageObject MyListsPageObject;


  @Test
  public void testCancelSearch_Ex3() throws InterruptedException {
    SearchPageObject = new SearchPageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Earth");
    int amount_articles_title = SearchPageObject.waitForSearchAmountResults("Earth"); // ищем вхождения в заголовках результатов поиска

    Assert.assertTrue(
              "No any articles title with search word",
              amount_articles_title > 1
    );

    SearchPageObject.clickCancelSearch(); // удаляем значение из поисковой строки

    int amount_articles_title_after_cancel = SearchPageObject.waitForSearchAmountResults("Earth"); // повторно ищем вхождения в заголовках результатов поиска

    Assert.assertFalse(
            "The are any articles title with search word",
            amount_articles_title_after_cancel > 1
    );
  }

  @Test
  public void testSave2ArticlesToList_Ex5() throws InterruptedException
  {
    SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject = new ArticlePageObject(driver);
    NavigationUI = new NavigationUI(driver);
    MyListsPageObject = new MyListsPageObject(driver);

    String search_word = "Programming language";
    String name_of_folder = "My list";


    // добавляем первую статью
    SearchPageObject.initSearchInput(); // кликаем на строку поиска
    SearchPageObject.typeSearchLine(search_word); // вводим поисковый запрос
    int amount_articles_title = SearchPageObject.amountOfAllArticlesTitleOnSearchPage(); // получаем количество заголовков

    // проверяем что статей больше чем 1
    Assert.assertTrue(
            "Cannot find any articles with title included search word " + search_word,
            amount_articles_title > 1
    );

    List<WebElement> articles_title = SearchPageObject.allArticlesTitleOnSearchPage(); // получаем список заголовков статей
    String article_title_from_search_1 = articles_title.get(0).getText(); // название первой статьи
    String article_title_from_search_2 = articles_title.get(1).getText(); // название второй статьи
    SearchPageObject.clickByArticleWithSubstring(article_title_from_search_1); // кликаем на заголовок 1-й статьи
    ArticlePageObject.addArticleToMyList(name_of_folder); // создаем папку списка и добавляем в нее первую статью
    ArticlePageObject.closeArticle(); // возврат на Главную
    SearchPageObject.initSearchInput(); // кликаем на строку поиска
    SearchPageObject.typeSearchLine(search_word); // вводим поисковый запрос
    SearchPageObject.clickByArticleWithSubstring(article_title_from_search_2); // кликаем на заголовок 2-й статьи
    ArticlePageObject.addArticle2ToMyList(name_of_folder); // добавляем в папку вторую статью
    ArticlePageObject.closeArticle(); // возврат на Главную
    NavigationUI.clickMyLists(); // переходим к спискам статей
    MyListsPageObject.openFolderByName(name_of_folder); // переходим в папку

    int amount_articles_title_in_list = MyListsPageObject.amountOfAllArticlesTitleOnList(); // получаем количество заголовков

    // убеждаемся что добавлено 2-е статьи
    Assert.assertTrue(
            "Amount of articles in list <> 2",
            amount_articles_title_in_list == 2
    );

    MyListsPageObject.swipeByArticleToDelete(article_title_from_search_1); // делаем свайп влево для первой статьи и удаляем ее

    int amount_articles_title_in_list_after_delete = MyListsPageObject.amountOfAllArticlesTitleOnList(); // получаем количество заголовков

    // проверяем что осталась только одна статья в списке
    Assert.assertTrue(
            "Amount of articles in list <> 1",
            amount_articles_title_in_list_after_delete == 1
    );

    MyListsPageObject.waitForArticleByTitleAndClick(article_title_from_search_2); // переходим на статью которая осталась
    WebElement title_element = ArticlePageObject.waitForTitleElement(); // получаем элемент с названием на экране статьи
    String article_title = title_element.getAttribute("text"); // получаем название статьи

    // сравниваем название статьи 2 из поиска и на экране статьи
    Assert.assertEquals(
            "We see unexpected title",
            article_title_from_search_2,
            article_title
    );
  }

  @Test
  public void testAssertTitle_Ex6() {
    SearchPageObject = new SearchPageObject(driver);
    ArticlePageObject = new ArticlePageObject(driver);

    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    ArticlePageObject.assertElementTitlePresent();
  }
}