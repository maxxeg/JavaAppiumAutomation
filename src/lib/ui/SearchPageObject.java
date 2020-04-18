package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SearchPageObject extends MainPageObject
{
  private static final String
          SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
          SEARCH_INPUT = "//*[contains(@text,'Search…')]",
          SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
          SEARCH_PLACEHOLDER = "org.wikipedia:id/search_src_text",
          SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]",
          SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
          SEARCH_EMPTY_RESULT_ELEMENT ="//*[@resource-id='org.wikipedia:id/search_empty_view']/*[@text='No results found']",
          SEARCH_RESULT_IN_TITLE_TPL = "//*[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget." +
                  "TextView[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'{TITLE}')]",
          SEARCH_RESULT_LOCATOR_IN_TITLE = "//*[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget." +
                  "TextView[@resource-id='org.wikipedia:id/page_list_item_title']",
          SEARCH_RESULT_IN_TITLE_AND_DESCRIPTION_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']/*[@class='android.widget.LinearLayout']" +
                  "[*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}'] and " +
                  "*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";

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
    this.waitForElementAndClick(
            By.xpath(SEARCH_INIT_ELEMENT),
            "Cannot find and click search init element",
            5);
    this.waitForElementPresent(
            By.xpath(SEARCH_INIT_ELEMENT),
            "Cannot find search input after clicking search init element");
  }

  public void waitForCancelButtonToAppear() {
    this.waitForElementPresent(
            By.id(SEARCH_CANCEL_BUTTON),
            "Cannot find search cancel button",
            5);
  }

  public void waitForCancelButtonToDisappear() {
    this.waitForElementNotPresent(
            By.id(SEARCH_CANCEL_BUTTON),
            "Search cancel button is still present",
            5);
  }

  public void clickCancelSearch() {
    this.waitForElementAndClick(
            By.id(SEARCH_CANCEL_BUTTON),
            "Cannot find and click search cancel button",
            5);
  }

  public void typeSearchLine(String search_line) {
    this.waitForElementAndSendKeys(
            By.xpath(SEARCH_INPUT),
            search_line, "Cannot find and type into search input",
            5);
  }

  public void waitForSearchResult(String substring) {
    String search_result_xpath = getResultSearchElement(substring);
    this.waitForElementPresent(
            By.xpath(search_result_xpath),
            "Cannot find search result with substring " + substring);
  }

  public int waitForSearchAmountResults(String substring) throws InterruptedException {
    String search_result_xpath = getResultSearchElementInTitle(substring);
    Thread.sleep(2000);
    List<WebElement> articles_title = searchArticles(By.xpath(search_result_xpath));
    return articles_title.size();
  }

  public List<WebElement> allArticlesTitleOnSearchPage() throws InterruptedException {
    Thread.sleep(2000);
    List<WebElement> articles_title = findElementsByLocator(
            By.xpath(SEARCH_RESULT_LOCATOR_IN_TITLE)
    );
    return articles_title;
  }

  public int amountOfAllArticlesTitleOnSearchPage() throws InterruptedException {
    Thread.sleep(2000);
    List<WebElement> articles_title = findElementsByLocator(
            By.xpath(SEARCH_RESULT_LOCATOR_IN_TITLE)
    );
    return articles_title.size();
  }

  public void waitForElementByTitleAndDescription(String title, String description) {
    String search_result_xpath = getResultSearchElementInTitleAndDescription(title, description);
    this.waitForElementPresent(
            By.xpath(search_result_xpath),
            "Cannot find search result by title '" + title + "' and description '" + description + "'",
            10
    );
  }

  public void clickByArticleWithSubstring(String substring) {
    String search_result_xpath = getResultSearchElement(substring);
    this.waitForElementAndClick(
            By.xpath(search_result_xpath),
            "Cannot find and click search result with substring " + substring,
            10);
  }

  public WebElement waitForSearchPlaceholder() {
    return this.waitForElementPresent(
            By.id(SEARCH_PLACEHOLDER),
            "Cannot find search placeholder",
            5);
  }

  public String getSearchPlaceholder() {
    WebElement search_placeholder = waitForSearchPlaceholder();
    return search_placeholder.getAttribute("text");
  }

  public int getAmountOfFoundArticles() {
    this.waitForElementPresent(
            By.xpath(SEARCH_RESULT_ELEMENT),
            "Cannot find anything by the request",
            15
    );
    return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
  }

  public void waitForEmptyResultsLabel() {
    this.waitForElementPresent(
            By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
            "Cannot find empty result element",
            15
    );
  }

  public void assertThereIsNotResultSearch() {
    this.assertElementNotPresent(
            By.xpath(SEARCH_RESULT_ELEMENT),
            "We supposed not to find any results"
    );
  }
}
