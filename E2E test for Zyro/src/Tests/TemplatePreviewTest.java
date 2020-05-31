package Tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TemplatePreviewTest {

	private static WebDriver driver;

	public static void InitDriver() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Gediminas\\Desktop\\chromedriver_win32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("incognito");
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test
	public void templatesPreviewTest() {
		
		String[] templatesArray = { "GUST", "ACACIA", "ARGYLE", "AXEL", "BIJOU", "BILLIE", "BLACKMORE", "BROOKE", "DALLAS", "DEXTER",
				"EREN", "EUGENIE", "FELICITY", "JAYE", "OAKLAND", "QUINN", "REESE", "RUDD", "BOUN", "LAURENT", "CARLO"};
		
		InitDriver();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get("https://zyro.com/");
		
		WebElement createFreeWebsiteBtn = driver.findElement(By.cssSelector("a[href='/templates']"));
		createFreeWebsiteBtn.click();
		
		// Can't it use because there is problems with data-qa attribute values 
		List<WebElement> templates = driver.findElements(By.cssSelector("div.template__circlebox"));

		for (int i = 0; i < templatesArray.length; i++) {

			String templateName = templatesArray[i];
			WebElement textUnderTemplate = driver.findElement(
					By.xpath("//div[@data-qa='templates-template-image-" + templateName.toLowerCase() + "']/../h3"));
			wait.until(ExpectedConditions.textToBePresentInElement(textUnderTemplate, templateName));

			String textUnderTemplateAsString = textUnderTemplate.getText();
			System.out.println(textUnderTemplateAsString);
			
			WebElement previewButton = driver.findElement(By.cssSelector("a[data-qa='templates-template-btn-preview-" + templateName.toLowerCase() + "']"));
			previewButton.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("button--plump-purple")));

			String templateNameWhenOpened = driver.getTitle();
			String templateNameWhenOpenedArray[] = templateNameWhenOpened.split(" ", 3);
			String titleSecondWord = templateNameWhenOpenedArray[1].toUpperCase();
			System.out.println(titleSecondWord);

			Assert.assertEquals(textUnderTemplateAsString, titleSecondWord);
			driver.navigate().back();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-qa='cookiesmodal-message-gotit'")));
		}
		
		driver.quit();

	}
}
