package org.scenarioo.selenium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.model.docu.entities.Page;
import org.scenarioo.model.docu.entities.Step;
import org.scenarioo.model.docu.entities.StepDescription;
import org.scenarioo.model.docu.entities.StepHtml;

public class ScenariooSeleniumListener extends AbstractWebDriverEventListener {
	
	private ScenarioDocuWriter writer;
	private ScenariooRule rule;

	private int currentIndex = 0;
	
	public ScenariooSeleniumListener(ScenarioDocuWriter writer, ScenariooRule rule) {
		this.writer = writer;
		this.rule = rule;
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		writeStep(driver);
	}
	
	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		writeStep(driver);
	}

	private void writeStep(WebDriver driver) {
		Step step = new Step();
		step.setHtml(new StepHtml(driver.getPageSource()));
		
		StepDescription stepDescription = new StepDescription();
		stepDescription.setTitle(driver.getTitle());
		stepDescription.setIndex(currentIndex);
		step.setStepDescription(stepDescription);
		
		step.setPage(new Page(sanitizeUrl(driver.getCurrentUrl())));
		
		TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
		byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
		writer.saveScreenshotAsPng(rule.getCurrentUsecase().getName(), rule.getCurrentScenario().getName(), currentIndex++, screenshot);
		writer.saveStep(rule.getCurrentUsecase(), rule.getCurrentScenario(), step);
	}
	
	private String sanitizeUrl(String currentUrl) {
		currentUrl = currentUrl.replaceAll("http://scenarioo.github.io/scenarioo-hello-world-app/", "");
		currentUrl = currentUrl.replaceAll("/", "#");
		
		return currentUrl;
	}
}
