package org.scenarioo.demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ShowNameTest {
	
	private static final String BASE_URL = "http://scenarioo.github.io/scenarioo-hello-world-app/";
	private WebDriver webDriver;
	
	@Before
	public void createDriver() {
		webDriver = new FirefoxDriver();
	}
	
	@Test
	public void showGenericName() {
		openStartpage();
		chooseEnterGenericName();
		enterName("Scenarioo User");
		assertName("Scenarioo User");
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
