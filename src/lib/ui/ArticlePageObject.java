package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject
{
  private static final String
          TITLE = "org.wikipedia:id/view_page_title_text",
          FOOTER_ELEMENT = "//*[@text='View page in browser']",
          OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
          OPTIONS_ADD_TO_MY_LIST_BUTTON = "//android.widget.TextView[@text='Add to reading list']",
          ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
          MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
          MY_LIST_OK_BUTTON = "//android.widget.Button[@text='OK']",
          CLOSE_ARTICLE_BUTTON ="//android.widget.ImageButton[@content-desc='Navigate up']",
          MY_LIST_EXISTING_FOLDER_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER}']";

  public ArticlePageObject(AppiumDriver driver) {
    super(driver);
  }

  /* TEMPLATES METHODS */
  private static String getFolderInListsElement(String name_of_folder) {
    return MY_LIST_EXISTING_FOLDER_TPL.replace("{FOLDER}", name_of_folder);
  }
  /* TEMPLATES METHODS */

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(
            By.id(TITLE),
            "Cannot find article title on page",
            15);
  }

  public String getArticleTitle() {
    WebElement title_element = waitForTitleElement();
    return title_element.getAttribute("text");
  }

  public void swipeToFooter() {
    this.swipeUpToFindElement(
            By.xpath(FOOTER_ELEMENT),
            "Cannot find the end of article",
            20);
  }

  public void addArticleToMyList(String name_of_folder) {
    this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON),
            "Cannot find button to open article options",
            5);

    this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "Cannot find option to add article to reading list",
            5);

    this.waitForElementAndClick(
            By.id(ADD_TO_MY_LIST_OVERLAY),
            "Cannot find button 'GOT IT' tip overlay",
            15);

    this.waitForElementAndClear(
            By.id(MY_LIST_NAME_INPUT),
            "Cannot find text field on name of list popup",
            5);

    this.waitForElementAndSendKeys(
            By.id(MY_LIST_NAME_INPUT),
            name_of_folder,
            "Cannot put any text in the text field on name of list popup",
            5);

    this.waitForElementAndClick(
            By.xpath(MY_LIST_OK_BUTTON),
            "Cannot press OK button",
            5);
  }

  public void addArticle2ToMyList(String name_of_folder) {
    this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON),
            "Cannot find button to open article options",
            5);

    this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "Cannot find option to add article to reading list",
            5);

    String folder_in_lists = getFolderInListsElement(name_of_folder);
    this.waitForElementAndClick(
              By.xpath(folder_in_lists),
              "Cannot find folder in lists",
              5
    );
  }

  public void closeArticle() {
    this.waitForElementAndClick(
            By.xpath(CLOSE_ARTICLE_BUTTON),
            "Cannot find Close button om article page",
            5);
  }

  public void assertElementTitlePresent() {
    this.assertElementPresent(
            By.id(TITLE),
            "We've not found title element"
    );
  }
}