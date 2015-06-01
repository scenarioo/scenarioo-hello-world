package org.scenarioo.demo;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.scenarioo.selenium.ScenariooRule;

public class ShowNameTest {
	
	@Rule
	public ScenariooRule scenariooRule = new ScenariooRule(new File("data"), "mybranch", "mybuild");
	
	private static final String BASE_URL = "http://scenarioo.github.io/scenarioo-hello-world-app/";
	private static WebDriver webDriver;
	
	@BeforeClass
	public static void createDriver() {
		webDriver = new FirefoxDriver();
	}
	
	@AfterClass
	public static void closeDriver() {
		webDriver.quit();
	}
	
	@Test
	public void showGenericName() {
		openStartpage();
		chooseEnterGenericName();
		enterName("Custom User");
		assertName("Custom User");
	}

	private void openStartpage() {
		webDriver.get(BASE_URL);
	}
	
	private void chooseEnterGenericName() {
		WebElement genericNameBox = webDriver.findElement(By.cssSelector("#uc1 a"));
		genericNameBox.click();
	}

	private void enterName(String name) {
		WebElement nameTextElement = webDriver.findElement(By.cssSelector("#nameInput"));
		nameTextElement.sendKeys(name);
		WebElement send = webDriver.findElement(By.cssSelector("#submitLink"));
		send.click();
	}

	private void assertName(String name) {
		WebElement userName = webDriver.findElement(By.cssSelector("#userName"));
		assertEquals(name, userName.getText());
	}
}
