package net.thucydides.junit.runners;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.WebDriverFactory;
import net.thucydides.junit.rules.DisableThucydidesHistoryRule;
import net.thucydides.samples.MultipleNonWebTestScenario;
import net.thucydides.samples.SingleNonWebTestScenario;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WhenLoggingTestScenarioResults extends AbstractTestStepRunnerTest {

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    FirefoxDriver firefoxDriver;

    EnvironmentVariables environmentVariables;

    @Rule
    public DisableThucydidesHistoryRule disableThucydidesHistoryRule = new DisableThucydidesHistoryRule();

    WebDriverFactory webDriverFactory;

    @Before
    public void createATestableDriverFactory() throws Exception {

        MockitoAnnotations.initMocks(this);

        environmentVariables = Injectors.getInjector().getProvider(EnvironmentVariables.class).get() ;
        webDriverFactory = new WebDriverFactory(environmentVariables);
        StepEventBus.getEventBus().clear();

    }


    @Test
    public void the_test_runner_records_the_name_of_the_test_scenario() throws InitializationError {

        ThucydidesRunner runner1 = new ThucydidesRunner(SingleNonWebTestScenario.class);
        ThucydidesRunner runner2 = new ThucydidesRunner(MultipleNonWebTestScenario.class);
        ThucydidesRunner runner3 = new ThucydidesRunner(MultipleNonWebTestScenario.class);
        runner1.run(new RunNotifier());
        runner2.run(new RunNotifier());
        runner3.run(new RunNotifier());
    }


}