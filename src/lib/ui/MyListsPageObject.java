package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class MyListsPageObject extends MainPageObject
{
  private static final String
          FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
          ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']",
          ARTICLE_IN_LIST_TITLE = "org.wikipedia:id/page_list_item_title";

  public MyListsPageObject(AppiumDriver driver) {
    super(driver);
  }

  /* TEMPLATES METHODS */
  private static String getFolderXpathByName(String name_of_folder) {
    return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
  }

  private static String getSavedArticleXpathByTitle(String article_title) {
    return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
  }
  /* TEMPLATES METHODS */

  public void openFolderByName(String name_of_folder) {
    String folder_name_xpath = getFolderXpathByName(name_of_folder);
    this.waitForElementAndClick(
            By.xpath(folder_name_xpath),
            "Cannot find folder by name " + name_of_folder,
            15);
  }

  public void waitForArticleToAppearByTitle(String article_title) {
    String article_title_xpath = getSavedArticleXpathByTitle(article_title);
    this.waitForElementPresent(
            By.xpath(article_title_xpath),
            "Cannot find saved article by title " + article_title,
            15);
  }

  public void waitForArticleToDisappearByTitle(String article_title) {
    String article_title_xpath = getSavedArticleXpathByTitle(article_title);
    this.waitForElementNotPresent(
            By.xpath(article_title_xpath),
            "Saved article still present by title " + article_title,
            15);
  }

  public void swipeByArticleToDelete(String article_title) {
    this.waitForArticleToAppearByTitle(article_title);
    String article_title_xpath = getSavedArticleXpathByTitle(article_title);
    this.swipeElementToLeft(
            By.xpath(article_title_xpath),
            "Cannot find saved article by title " + article_title);
    this.waitForArticleToDisappearByTitle(article_title);
  }

  public void waitForArticleByTitleAndClick(String article_title) {
    String article_title_xpath = getSavedArticleXpathByTitle(article_title);
    this.waitForElementAndClick(
            By.xpath(article_title_xpath),
            "Cannot find article in list folder " + article_title,
            15);
  }

  public int amountOfAllArticlesTitleOnList() throws InterruptedException {
    List<WebElement> articles_title = findElementsByLocator(
            By.id(ARTICLE_IN_LIST_TITLE)
    );
    return articles_title.size();
  }
}
