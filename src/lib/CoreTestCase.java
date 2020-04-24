package lib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase
{
  private static final String
          PLATFORM_IOS = "ios",
          PLATFORM_ANDROID = "android";

  protected AppiumDriver driver;
  private static String AppiumUrl = "http://127.0.0.1:4723/wd/hub";

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    DesiredCapabilities capabilities = this.getCapabilitiesPlatformEnv();

    this.rotateScreenPortrait();
  }

  @Override
  protected void tearDown() throws Exception {
    driver.quit();

    super.tearDown();
  }

  protected void rotateScreenPortrait() {
    driver.rotate(ScreenOrientation.PORTRAIT);
  }

  protected void rotateScreenLandscape() {
    driver.rotate(ScreenOrientation.LANDSCAPE);
  }

  protected void backgroundApp(int seconds) {
    driver.runAppInBackground(Duration.ofSeconds(seconds));
  }

  private DesiredCapabilities getCapabilitiesPlatformEnv() throws Exception {
    String platform = System.getenv("PLATFORM");
    DesiredCapabilities capabilities = new DesiredCapabilities();

    if (platform.equals(PLATFORM_ANDROID)) {
      capabilities.setCapability("platformName", "Android");
      capabilities.setCapability("deviceName", "AndroidTestDevice");
      capabilities.setCapability("platformVersion", "8");
      capabilities.setCapability("automationName", "Appium");
      capabilities.setCapability("appPackage", "org.wikipedia");
      capabilities.setCapability("appActivity", ".main.MainActivity");
      capabilities.setCapability("app", "C:/Users/maxim.egorov/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");
      driver = new AndroidDriver(new URL(AppiumUrl), capabilities);
    } else if (platform.equals(PLATFORM_IOS)) {
      capabilities.setCapability("platformName", "iOS");
      capabilities.setCapability("deviceName", "iPhone SE 13.4");
      capabilities.setCapability("platformVersion", "13.4");
      capabilities.setCapability("app", "/Users/rtlabs/Desktop/JavaAppiumAutomation/apks/Wikipedia.app");
      driver = new IOSDriver(new URL(AppiumUrl), capabilities);
    } else {
      throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
    }

    return capabilities;
  }

}
