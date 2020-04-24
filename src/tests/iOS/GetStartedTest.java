package tests.iOS;
import lib.iOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends iOSTestCase
{
    private WelcomePageObject WelcomePageObject;

    @Test
    public void testPassThroughWelcome() {
        WelcomePageObject = new WelcomePageObject(driver);

        WelcomePageObject.waitForLearnMoreLink();
        WelcomePageObject.clickNextButton();
        WelcomePageObject.waitForNewWayToExploreText();
        WelcomePageObject.clickNextButton();
        WelcomePageObject.waitForAddOrEditPreferredLangLink();
        WelcomePageObject.clickNextButton();
        WelcomePageObject.waitForLearnMoreAboutDataCollectedLink();
        WelcomePageObject.clickGetStartedButton();
    }
}
