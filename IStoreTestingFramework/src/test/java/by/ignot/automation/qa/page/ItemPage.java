package by.ignot.automation.qa.page;

import by.ignot.automation.qa.util.LogProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ItemPage extends CommonPage{
    @FindBy(xpath = "//button[@ng-if=\"!linkOrButton(page.product.id) && page.product.prices.status == 0\"]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//a[@ng-href=\"cart\"]")
    private WebElement afterAddToCartButton;

    @FindBy(xpath = "//div[@class=\"img ng-scope slick-slide slick-current slick-active\"]")
    private WebElement itemImage;

    @FindBy(xpath = "//a[@ng-if=\"linkOrButton(page.product.id)\"][@class=\"button ng-scope\"]")
    private WebElement goToCartButton;

    @FindBy(xpath = "//button[@ng-class=\"{'active': isProductFavorite(page.product.id)}\"]")
    private WebElement addToFavouritesButton;

    @FindBy(xpath = "//span[@itemprop=\"sku\"]")
    private WebElement itemVendorCode;

    @FindBy(xpath = "//a[@class=\"icon favorite active ng-scope\"]")
    private WebElement goToFavouritesButton;

    public ItemPage(WebDriver driver){
        super(driver, LogProvider.getLog());
        PageFactory.initElements(driver, this);
    }

    public ItemPage(WebDriver driver, String pageURL){
        super(driver, LogProvider.getLog(), pageURL);
        PageFactory.initElements(driver, this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ItemPage openPage(){
        log.info("Opening item page");
        driver.get(pageURL);
        return this;
    }

    public String getItemVendorCode() {
        log.info("Getting item's vendor code");

        new WebDriverWait(driver, 10)
                .until(_driver -> !this.itemVendorCode.getText().isEmpty());
        return itemVendorCode.getText();
    }

    public ItemPage addToCart() {
        log.info("Adding item to cart");
        addToCartButton.click();
        new WebDriverWait(driver, WAIT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(afterAddToCartButton));
        return this;
    }

    public ItemPage addToFavourites() {
        log.info("Adding item to favourites list");

        new WebDriverWait(driver, WAIT_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(itemImage));

        new WebDriverWait(driver, WAIT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(addToFavouritesButton));

        addToFavouritesButton.click();

        return this;
    }

    public FavouritesPage goToFavourites(){
        log.info("Going to favourites");
        goToFavouritesButton.click();
        return new FavouritesPage(driver);
    }

    public CartPage goToCart() {
        log.info("Going to cart");
        goToCartButton.click();
        return new CartPage(driver);
    }

}