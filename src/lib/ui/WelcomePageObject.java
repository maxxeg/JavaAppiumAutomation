package lib.ui;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject
{
    private static final String
    STEP_LEARN_MORE_LINK = "Learn more about Wikipedia",
    STEP_NEW_WAY_TO_EXPLORE_TEXT = "New ways to explore",
    STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "Add or edit preferred languages",
    STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "Learn more about data collected",
    NEXT_LINK = "next",
    GET_STARTED_BUTTON = "Get started";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink(){
        this.waitForElementPresent(
                By.id(STEP_LEARN_MORE_LINK),
                "Cannot find '" + STEP_LEARN_MORE_LINK + "' link",
                10);
    }

    public void waitForNewWayToExploreText(){
        this.waitForElementPresent(
                By.id(STEP_NEW_WAY_TO_EXPLORE_TEXT),
                "Cannot find '" + STEP_NEW_WAY_TO_EXPLORE_TEXT + "' text",
                10);
    }

    public void waitForAddOrEditPreferredLangLink(){
        this.waitForElementPresent(
                By.id(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK),
                "Cannot find '" + STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK + "' link",
                10);
    }

    public void waitForLearnMoreAboutDataCollectedLink(){
        this.waitForElementPresent(
                By.id(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK),
                "Cannot find '" + STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK + "' link",
                10);
    }

    public void clickNextButton(){
        this.waitForElementAndClick(
                By.id(NEXT_LINK),
                "Cannot find and click '" + NEXT_LINK + "' button",
                10);
    }

    public void clickGetStartedButton(){
        this.waitForElementAndClick(
                By.id(GET_STARTED_BUTTON),
                "Cannot find and click '" + GET_STARTED_BUTTON + "' button",
                10);
    }
}
