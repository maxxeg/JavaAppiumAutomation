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

public class HomeWork
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
    if (driver.getOrientation() == ScreenOrientation.LANDSCAPE) driver.rotate(ScreenOrientation.PORTRAIT);
    //driver.resetApp();
    driver.quit();
  }

  /*
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

  @Test
  public void testSave2ArticlesToList_Ex5() throws InterruptedException
  {

    String search_word = "Languages of";
    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']";
    String name_of_folder = "My list";
    String article_in_list_title_locator ="org.wikipedia:id/page_list_item_title";

    // в цикле пишем в список 2-е статьи
    for (int i = 0; i <= 1; i++)
    {

    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    // вводим поисковый запрос
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_word,
            "Cannot find search input",
            5
    );

    // находим все статьи в результатах поиска (в видимой области)
    Thread.sleep(2000);
    List<WebElement> articles_title = findElementsByLocator(
            By.xpath(search_result_locator)
    );

    Assert.assertTrue(
            "Cannot find any articles with title included search word " + search_word,
            articles_title.size() > 0
    );

      // переходим на статью
      waitForElementAndClick(
              By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'" + articles_title.get(i).getText() +"')]"),
              "Cannot go to article '" + articles_title.get(i).getText() +"'",
              5
      );

      Thread.sleep(2000);
      System.out.println("");

      // click "More options"
      waitForElementAndClick(
              By.xpath("//android.widget.ImageView[@content-desc='More options']"),
              "Cannot find button to open article options",
              5
      );

      // click "Add to reading list"
      waitForElementAndClick(
              By.xpath("//*[@text='Add to reading list']"),
              "Cannot find option to add article to reading list",
              5
      );

      // для первого добавления
      if (i == 0)
      {
        // click "GOT IT" button
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find button 'GOT IT' on popup",
                10
        );

        // очищаем плейсхолдер названия группы
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find text field on name of list popup",
                5
        );

        // вводим название группы
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put any text in the text field on name of list popup",
                5
        );

        // press "OK" button
        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot press OK button",
                5
        );
      }

      // для второго добавления
      if (i == 1)
      {
        // выбираем нужную папку
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + name_of_folder + "']"),
                "Cannot find folder in lists",
                5
        );

      }

      // возврат на Главную
      waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find Close button om article page",
                5
        );

    }
    // конец цикла - добавлены 2-е статьи

    // переходим к спискам статей
    waitForElementAndClick(
              By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
              "Cannot find lists icon",
              5
      );

    // переходим к нужному списку
    waitForElementAndClick(
              By.id("org.wikipedia:id/item_title"),
              "Cannot find list in My lists",
              5
      );

    // проверяем что добавлено 2-е статьи
    Thread.sleep(2000);
    List<WebElement> articles = findElementsByLocator(
            By.id(article_in_list_title_locator)
    );

    Assert.assertTrue(
            "Amount of articles in list <> 2",
            articles.size() == 2
    );

    String second_title = articles.get(1).getText();

    // делаем свайп влево для первой статьи
    swipeElementToLeft(
            By.xpath("//*[@text='" + articles.get(0).getText() + "']"),
            "Cannot find saved article"
    );

    // проверяем что осталась только одна статья в списке
    Thread.sleep(2000);
    List<WebElement> article = findElementsByLocator(
            By.id(article_in_list_title_locator)
    );

    Assert.assertTrue(
            "Amount of articles in list <> 1",
            article.size() == 1
    );

    // переходим на статью которая осталась
    waitForElementAndClick(
            By.xpath("//*[@text='" + second_title + "']"),
            "Cannot find second article in list folder",
            5
    );

    // проверям что название правильное
    // Проверяем заголовок статьи
    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    String article_title = title_element.getAttribute("text");

    Assert.assertEquals(
            "We see unexpected title",
            second_title,
            article_title
    );

  }

  @Test
  public void testAssertTitle_Ex6() throws InterruptedException {

    String search_word = "Languages of";
    String article_title = "Languages of India";
    String title_locator = "//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='" + article_title + "']";

    // ищем поле поле поиска и кликаем
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    // вводим поисковый запрос
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            search_word,
            "Cannot find search input",
            5
    );

    // переходим на статью
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'" + article_title +"')]"),
            "Cannot go to article '" + article_title +"'",
            5
    );

    // проверяем есть ли элемент на странице
    assertElementPresent(
            By.xpath(title_locator),
            "We've not found title " + article_title
    );

  }
  */

  @Test
  public void testChangeScreenOrientation_Ex7() throws InterruptedException {

    // попорачиваем в альбомную
    driver.rotate(ScreenOrientation.LANDSCAPE);

    Thread.sleep(1000);

    // тест падает с ошибкой
    Assert.assertFalse(
            "Эмуляция ошибки для падения теста после поворота экрана устройства",
            1 == 1
    );

  }

  @Test
  public void testOrientationAfterPreviousTest() throws InterruptedException
  {

    Thread.sleep(1000);

    // проверяем ориентацию, если альбомная - тест падает
    Assert.assertTrue(
            "Ориентация экрана устройства Альбомная - ожидалось что ориентация будет сброшена к Портретной",
            driver.getOrientation() == ScreenOrientation.PORTRAIT
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
            .waitAction(300)
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

  private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
  {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    return element.getAttribute(attribute);
  }

  private List<WebElement> findElementsByLocator(By by)
  {
    List<WebElement> elements = driver.findElements(by);
    return elements;
  }

  private void assertElementPresent(By by, String error_message)
  {
    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements == 0){
      String default_message = "An element '" + by.toString() + "' supposed present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }
}
