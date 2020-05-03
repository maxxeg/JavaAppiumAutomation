package lib.ui.android;
import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject
{
    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
        SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_PLACEHOLDER = "id:org.wikipedia:id/search_src_text";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_empty_view']/*[@text='No results found']";
        SEARCH_RESULT_IN_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget." +
                        "TextView[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'{TITLE}')]";
        SEARCH_RESULT_LOCATOR_IN_TITLE = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout/android.widget.LinearLayout/android.widget." +
                        "TextView[@resource-id='org.wikipedia:id/page_list_item_title']";
        SEARCH_RESULT_IN_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/*[@class='android.widget.LinearLayout']" +
                        "[*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}'] and " +
                        "*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";
    }
    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
