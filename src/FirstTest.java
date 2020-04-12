import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.util.List;

public class FirstTest
{
  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception
  {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "8");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "C:/Users/maxim.egorov/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown()
  {
    driver.quit();
  }

  @Test
  public void firstTest() throws InterruptedException
  {

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot find search input",
            5
    );

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
            "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
            15
    );

  }

  @Test
  public void testCancelSearch()
  {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Cannot find search input",
            5
    );

    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search field",
            5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find search close button",
            5
    );

    waitElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "Search close button is still not present on the page",
            5
    );

  }

  @Test
  public void testCompareArticleTitle()
  {

    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    // проверяем наличие текста “Search…” в строке поиска
    WebElement search_placeholder = waitForElementPresent(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search placeholder",
            5
    );

    String placeholder = search_placeholder.getAttribute("text");

    Assert.assertEquals(
            "Not present search placeholder",
            "Search…",
            placeholder
    );

    // вводим Java в поле поиска
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Cannot find search input",
            5
    );

    // Ищем строку в результатах поиска
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
            "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
            15
    );

    // Проверяем заголовок статьи
    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find 'Java (programming language)' as article title",
            15
    );

    String article_title = title_element.getAttribute("text");

    Assert.assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            article_title
    );

  }

  @Test
  public void testSwipeArticle()
  {

    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

        // вводим Java в поле поиска
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Appium",
            "Cannot find search input",
            5
    );

    // Ищем строку в результатах поиска
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find result on search page",
            5
    );

    // Проверяем заголовок статьи
    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find 'Java (programming language)' as article title",
            15
    );

    swipeUpToFindElement(
            By.xpath("//*[@text='View page in browser']"),
            "Not find footer and end of article",
            20
    );

  }

  @Test
  public void saveFirstArticleToMyList()
  {
    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    // вводим Java в поле поиска
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Cannot find search input",
            5
    );

    // Ищем строку в результатах поиска
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
            "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
            5
    );

    // Проверяем заголовок статьи
    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find 'Java (programming language)' as article title",
            15
    );

    waitForElementAndClick(
      By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );

    waitForElementAndClick(
      By.xpath("//android.widget.TextView[@text='Add to reading list']"),
      "Cannot find option to add article to reading list",
            5
    );

    waitForElementAndClick(
      By.id("org.wikipedia:id/onboarding_button"),
      "Cannot find button 'GOT IT' on popup",
      15
    );

    waitForElementAndClear(
      By.id("org.wikipedia:id/text_input"),
      "Cannot find text field on name of list popup",
      5
    );

    String name_of_folder = "Learning programming";

    waitForElementAndSendKeys(
       By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot put any text in the text field on name of list popup",
            5
    );

    waitForElementAndClick(
      By.xpath("//android.widget.Button[@text='OK']"),
            "Cannot press OK button",
            5
    );

    waitForElementAndClick(
      By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot find Close button om article page",
            5
    );

    waitForElementAndClick(
      By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find lists icon",
            5
    );

    waitForElementAndClick(
      By.xpath("//*[@text='" + name_of_folder + "']"),
      "Cannot find list in My lists",
      5
    );

    waitForElementPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find article in list",
            5
    );

    swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article"
    );

    waitElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            5
    );


  }

  @Test
  public void testAmountOfNotEmptySearch()
  {
    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    // вводим поисковый запрос в поле поиска
    String search_line = "Linkin Park Discography";
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Cannot find search input",
            5
    );

    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    waitForElementPresent(
            By.xpath(search_result_locator),
            "Cannot find anything by the request " + search_line,
            15
    );

    int amount_of_search_results = getAmountOfElements(
            By.xpath(search_result_locator)
    );

    Assert.assertTrue(
            "We found too few results",
            amount_of_search_results > 0
    );

  }

  @Test
  public void testAmountOfEmptySearch()
  {
    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    // вводим поисковый запрос в поле поиска
    String search_line = "terbtreyjfmjkjh";
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Cannot find search input",
            5
    );

    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String empty_result_lable = "//*[@resource-id='org.wikipedia:id/search_empty_view']/*[@text='No results found']";

    waitForElementPresent(
            By.xpath(empty_result_lable),
            "Cannot find empty result label by the request " + search_line,
            15
    );

    assertElementNotPresent(
            By.xpath(search_result_locator),
            "We've found some results by request " + search_line
    );

  }

  @Test
  public void testChangeScreenOrientationOnSearchResults()
  {
    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    // вводим поисковый запрос в поле поиска
    String search_line = "Java";
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Cannot find search input",
            5
    );

    String search_result_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]";

    // Ищем строку в результатах поиска
    waitForElementAndClick(
            By.xpath(search_result_locator),
            "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
            15
    );

    String title_before_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    Assert.assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_rotation
    );

    driver.rotate(ScreenOrientation.PORTRAIT);

    String title_after_second_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );

    Assert.assertEquals(
            "Article title have been changed after screen rotation",
            title_after_rotation,
            title_after_second_rotation
    );
  }

  @Test
  public void testCheckSearchArticleInBackground()
  {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    String search_line = "Java";
    String article_descr = "Object-oriented programming language";
    String article_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'" + article_descr +"')]";

    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_line,
            "Cannot find search input",
            5
    );

    waitForElementPresent(
            By.xpath(article_locator),
            "Cannot find '" + article_descr + "' topic searching by '" + search_line + "'",
            5
    );

    driver.runAppInBackground(2);

    // Ищем строку в результатах поиска
    waitForElementPresent(
            By.xpath(article_locator),
            "Cannot find '" + article_descr + "' topic searching by '" + search_line + "' after returning from background",
            5
    );
  }

  @Test
  public void testSearchHomework() throws InterruptedException {

    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            2
    );

    // вводим что-то в поле поиска
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Earth",
            "Cannot find search input",
            2
    );

    // Ищем вхождения в заголовках результатов поиска
    Thread.sleep(2000);
    String xpath_search = "//*[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'Earth')]";
    List<WebElement> articles_title = searchArticles(By.xpath(xpath_search));

      Assert.assertTrue(
              "No any articles title with search word",
              articles_title.size() > 1
      );

      // Отменяем поиск
      waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find search close button",
            5
    );

    // Проверяем что нет результатов поиска
    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/search_empty_message"),
            "Cannot find search_empty_message",
            5
    );

  }

  @Test
  public void testSearchHomeworkEx4() throws InterruptedException
  {
    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            2
    );

    // вводим что-то в поле поиска
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Earth",
            "Cannot find search input",
            2
    );

    // Считаем количество заголовоков статей в результататх поиска, поместившихся в видимой области экрана
    Thread.sleep(2000);
    String xpath_search = "//*[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']";
    List<WebElement> articles_title = searchArticles(By.xpath(xpath_search));

    // Ищем вхождения в заголовках результатов поиска
    int titles_num_fact = 0;
    for (WebElement title : articles_title)
    {
      if (title.getText().matches("(.*)Earth(.*)")) titles_num_fact++;
    }

    Assert.assertTrue(
            "Some articles title not included search word",
            articles_title.size() == titles_num_fact
    );

  }

  private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
  {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by)
    );
  }

  private WebElement waitForElementPresent(By by, String error_message)
  {
    return waitForElementPresent(by, error_message, 5);
  }

  private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
  {
    WebElement element = waitForElementPresent(by, error_message, 5);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
  {
    WebElement element = waitForElementPresent(by, error_message, 5);
    element.sendKeys(value);
    return element;
  }

  private boolean waitElementNotPresent(By by, String error_message, long timeoutInSeconds)
  {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
    );
  }

  private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
  {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
  }

  private List<WebElement> searchArticles(By by)
  {
    List<WebElement> articles_title = driver.findElements(by);
    return articles_title;
  }

  protected void swipeUp(int timeOfSwipe)
  {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);
    action
            .press(x, start_y)
            .waitAction(timeOfSwipe)
            .moveTo(x, end_y)
            .release()
            .perform();
  }

  protected void swipeUpQuick()
  {
    swipeUp(200);
  }

  protected void swipeUpToFindElement(By by, String error_message, int max_swipes)
  {
    int already_swiped = 0;
    while (driver.findElements(by).size() == 0){

      if (already_swiped > max_swipes){
        waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
        return;
      }

      swipeUpQuick();
      ++already_swiped;
    }
  }

  protected void swipeElementToLeft(By by, String error_message)
  {
    WebElement element = waitForElementPresent(by,
            error_message,
            10);

    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();
    int upper_y = element.getLocation().getY();
    int lower_y = upper_y + element.getSize().getHeight();
    int middle_y = (upper_y + lower_y) / 2;

    TouchAction action = new TouchAction(driver);
    action
            .press(right_x, middle_y)
            .waitAction(150)
            .moveTo(left_x, middle_y)
            .release().perform();
  }

  private int getAmountOfElements(By by)
  {
    List elements = driver.findElements(by);
    return elements.size();
  }

  private void assertElementNotPresent(By by, String error_message)
  {
    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements > 0){
      String default_message = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long tineoutInSeconds)
  {
    WebElement element = waitForElementPresent(by, error_message, tineoutInSeconds);
    return element.getAttribute(attribute);
  }

}
