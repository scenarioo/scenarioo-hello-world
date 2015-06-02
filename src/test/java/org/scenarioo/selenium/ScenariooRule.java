package org.scenarioo.selenium;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.model.docu.entities.Scenario;
import org.scenarioo.model.docu.entities.UseCase;

public class ScenariooRule implements TestRule {

	private ScenarioDocuWriter writer;
	private UseCase currentUsecase;
	private Scenario currentScenario;
	
	public ScenariooRule(ScenarioDocuWriter writer) {
		this.writer = writer;
	}
	
	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				saveUseCase(description);
				saveScenario(description);
				base.evaluate();
			}

			private void saveScenario(Description description) {
				Scenario scenario = new Scenario();
				scenario.setName(description.getMethodName());
				currentScenario = scenario;
				writer.saveScenario(getUseCaseName(description), scenario);
			}


			private void saveUseCase(Description description) {
				UseCase useCase = new UseCase();
				useCase.setName(getUseCaseName(description));
				currentUsecase = useCase;
				writer.saveUseCase(useCase);
			}
			
			private String getUseCaseName(Description description) {
				return description.getTestClass().getSimpleName();
			}
		};
	}

	public Scenario getCurrentScenario() {
		return currentScenario;
	}
	
	public UseCase getCurrentUsecase() {
		return currentUsecase;
	}
}
