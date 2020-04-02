import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;

public class FirstTest
{
  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception
  {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "9");
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

}
