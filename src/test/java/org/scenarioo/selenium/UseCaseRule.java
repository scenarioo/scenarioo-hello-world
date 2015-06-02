package org.scenarioo.selenium;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.scenarioo.api.ScenarioDocuWriter;
import org.scenarioo.model.docu.entities.UseCase;

public class UseCaseRule implements TestRule {


	private ScenarioDocuWriter writer;
	private UseCase currentUseCase;
	
	public UseCaseRule(ScenarioDocuWriter writer) {
		this.writer = writer;
	}
	
	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				saveUseCase(description);
				base.evaluate();
			}

			private void saveUseCase(Description description) {
				UseCase useCase = new UseCase();
				useCase.setName(description.getTestClass().getSimpleName());
				currentUseCase = useCase;
				writer.saveUseCase(useCase);
			}

		};
	}

	public UseCase getCurrentUseCase() {
		return currentUseCase;
	}
}
