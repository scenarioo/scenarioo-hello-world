package org.scenarioo.selenium;

import java.io.File;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.model.docu.entities.Scenario;
import org.scenarioo.model.docu.entities.UseCase;

public class ScenariooRule implements TestRule {

	private ScenarioDocuWriter writer;
	
	public ScenariooRule(File buildDirectory, String branch, String build) {
		this.writer = new ScenarioDocuWriter(buildDirectory, branch, build);
	}
	
	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				createUseCaseDirectory(description);
				createScenarioDirectory(description);
				base.evaluate();
			}

			private void createScenarioDirectory(Description description) {
				Scenario scenario = new Scenario();
				scenario.setName(description.getMethodName());
				writer.saveScenario(getUseCaseName(description), scenario);
			}


			private void createUseCaseDirectory(Description description) {
				UseCase useCase = new UseCase();
				useCase.setName(getUseCaseName(description));
				writer.saveUseCase(useCase );
			}
			
			private String getUseCaseName(Description description) {
				return description.getTestClass().getSimpleName();
			}
		};
	}

}
