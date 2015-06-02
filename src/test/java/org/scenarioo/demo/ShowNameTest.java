package org.scenarioo.demo;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.selenium.ScenarioRule;
import org.scenarioo.selenium.ScenariooSeleniumListener;
import org.scenarioo.selenium.UseCaseRule;

public class ShowNameTest {
	
	private static ScenarioDocuWriter writer = new ScenarioDocuWriter(new File("data"), "mybranch", "mybuild");
	
	@ClassRule
	public static UseCaseRule usecaseRule = new UseCaseRule(writer);
	
	@Rule
	public ScenarioRule scenariooRule = new ScenarioRule(writer, usecaseRule);
	
	private static final String BASE_URL = "http://scenarioo.github.io/scenarioo-hello-world-app/index.html";
	private WebDriver webDriver;
	
	@Before
	public void createDriver() {
		EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(new FirefoxDriver());
		eventFiringWebDriver.register(new ScenariooSeleniumListener(writer, usecaseRule, scenariooRule));
		webDriver = eventFiringWebDriver;
	}
	
	@After
	public void closeDriver() {
		webDriver.quit();
	}
	
	@AfterClass
	public static void flushContent() {
		writer.flush();
	}
	
	@Test
	public void showGenericName() {
		openStartpage();
		chooseEnterGenericName();
		enterName("Custom User");
		assertName("Custom User");
	}

	@Test
	public void showFixedName() {
		openStartpage();
		chooseFixedName();
		assertName("Scenarioo User");
	}	
	


	private void openStartpage() {
		webDriver.get(BASE_URL);
	}
	
	private void chooseEnterGenericName() {
		WebElement genericNameBox = webDriver.findElement(By.cssSelector("#uc1 a"));
		genericNameBox.click();
	}
	
	private void chooseFixedName() {
		WebElement box = webDriver.findElement(By.cssSelector("#uc2 a"));
		box.click();		
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
