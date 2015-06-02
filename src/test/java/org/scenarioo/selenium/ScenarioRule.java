package org.scenarioo.selenium;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.model.docu.entities.Scenario;

public class ScenarioRule implements TestRule {

	private ScenarioDocuWriter writer;
	private Scenario currentScenario;
	private UseCaseRule useCaseRule;
	
	public ScenarioRule(ScenarioDocuWriter writer, UseCaseRule useCaseRule) {
		this.writer = writer;
		this.useCaseRule = useCaseRule;
	}
	
	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				saveScenario(description);
				base.evaluate();
			}

			private void saveScenario(Description description) {
				Scenario scenario = new Scenario();
				scenario.setName(description.getMethodName());
				currentScenario = scenario;
				writer.saveScenario(useCaseRule.getCurrentUseCase(), scenario);
			}

		};
	}

	public Scenario getCurrentScenario() {
		return currentScenario;
	}
}
