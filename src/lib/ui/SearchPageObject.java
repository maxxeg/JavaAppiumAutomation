package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

abstract public class SearchPageObject extends MainPageObject
{
  protected static String
          SEARCH_INIT_ELEMENT,
          SEARCH_INPUT,
          SEARCH_CANCEL_BUTTON,
          SEARCH_PLACEHOLDER,
          SEARCH_RESULT_BY_SUBSTRING_TPL,
          SEARCH_RESULT_ELEMENT,
          SEARCH_EMPTY_RESULT_ELEMENT,
          SEARCH_RESULT_IN_TITLE_TPL,
          SEARCH_RESULT_LOCATOR_IN_TITLE,
          SEARCH_RESULT_IN_TITLE_AND_DESCRIPTION_TPL;

  public SearchPageObject(AppiumDriver driver) {
    super(driver);
  }

  /* TEMPLATES METHODS - START */
  private static String getResultSearchElement(String substring) {
    return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
  }

  private static String getResultSearchElementInTitle(String substring) {
    return SEARCH_RESULT_IN_TITLE_TPL.replace("{TITLE}", substring);
  }

  private static String getResultSearchElementInTitleAndDescription(String title, String description) {
    return SEARCH_RESULT_IN_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
  }
  /* TEMPLATES METHODS - END */

  public void initSearchInput() {
    this.waitForElementAndClick(SEARCH_INIT_ELEMENT,"Cannot find and click search init element",5);
    this.waitForElementPresent(SEARCH_INIT_ELEMENT,"Cannot find search input after clicking search init element");
  }

  public void waitForCancelButtonToAppear() {
    this.waitForElementPresent(SEARCH_CANCEL_BUTTON,"Cannot find search cancel button",5);
  }

  public void waitForCancelButtonToDisappear() {
    this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON,"Search cancel button is still present",5);
  }

  public void clickCancelSearch() {
    this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,"Cannot find and click search cancel button",5);
  }

  public void typeSearchLine(String search_line) {
    this.waitForElementAndSendKeys(SEARCH_INPUT,search_line, "Cannot find and type into search input",5);
  }

  public void waitForSearchResult(String substring) {
    String search_result_xpath = getResultSearchElement(substring);
    this.waitForElementPresent(search_result_xpath,"Cannot find search result with substring " + substring);
  }

  public int waitForSearchAmountResults(String substring) throws InterruptedException {
    String search_result_xpath = getResultSearchElementInTitle(substring);
    Thread.sleep(2000);
    List<WebElement> articles_title = searchArticles(search_result_xpath);
    return articles_title.size();
  }

  public List<WebElement> allArticlesTitleOnSearchPage() throws InterruptedException {
    Thread.sleep(2000);
    List<WebElement> articles_title = findElementsByLocator(SEARCH_RESULT_LOCATOR_IN_TITLE);
    return articles_title;
  }

  public int amountOfAllArticlesTitleOnSearchPage() throws InterruptedException {
    Thread.sleep(3000);
    List<WebElement> articles_title = findElementsByLocator(SEARCH_RESULT_LOCATOR_IN_TITLE);
    return articles_title.size();
  }

  public void waitForElementByTitleAndDescription(String title, String description) {
    String search_result_xpath = getResultSearchElementInTitleAndDescription(title, description);
    this.waitForElementPresent(search_result_xpath,"Cannot find search result by title '" + title + "' and description '" + description + "'",10);
  }

  public void waitForElementByTitle(String title) {
    String search_result_xpath = getResultSearchElementInTitleAndDescription(title, "");
    this.waitForElementPresent(search_result_xpath,"Cannot find search result by title '" + title + "'",10);
  }

  public void clickByArticleWithSubstring(String substring) {
    String search_result_xpath = getResultSearchElement(substring);
    this.waitForElementAndClick(search_result_xpath,"Cannot find and click search result with substring " + substring,10);
  }

  public WebElement waitForSearchPlaceholder() {
    return this.waitForElementPresent(SEARCH_PLACEHOLDER,"Cannot find search placeholder",5);
  }

  public String getSearchPlaceholder() {
    WebElement search_placeholder = waitForSearchPlaceholder();
    return search_placeholder.getAttribute("text");
  }

  public int getAmountOfFoundArticles() {
    this.waitForElementPresent(SEARCH_RESULT_ELEMENT,"Cannot find anything by the request",15);
    return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
  }

  public void waitForEmptyResultsLabel() {
    this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT,"Cannot find empty result element",15
    );
  }

  public void assertThereIsNotResultSearch() {
    this.assertElementNotPresent(SEARCH_RESULT_ELEMENT,"We supposed not to find any results");
  }
}
