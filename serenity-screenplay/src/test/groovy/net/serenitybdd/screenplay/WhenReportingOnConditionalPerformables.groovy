package net.serenitybdd.screenplay

import net.serenitybdd.screenplay.conditions.Check
import net.thucydides.core.model.TestResult
import net.thucydides.core.steps.BaseStepListener
import net.thucydides.core.steps.StepEventBus
import spock.lang.Specification

import java.nio.file.Files

class WhenReportingOnConditionalPerformables extends Specification {

    File temporaryDirectory
    BaseStepListener listener = new BaseStepListener(temporaryDirectory)

    def setup() {
        temporaryDirectory = Files.createTempDirectory("tmp").toFile();
        temporaryDirectory.deleteOnExit();

        StepEventBus.eventBus.clear()
        StepEventBus.eventBus.registerListener(listener)
        StepEventBus.eventBus.testStarted("some test")
    }

    def "A task with a conditional Performable will report the AndIfSo task if conditional returns true"() {

        given:
        Performable eatsAnApple = new EatsAnApple()
        when:
        Actor.named("eddie").attemptsTo(Check.whether(true).andIfSo(eatsAnApple));
        then:
        testPassed()
        and:
        testOutcomeContainsStep("Eddie eats an apple")
    }

    def "A task with a conditional Performable will report the Otherwise task if conditional returns false"() {

        given:
        Performable eatsAnApple = new EatsAnApple()
        when:
        Actor.named("eddie").attemptsTo(Check.whether(false).otherwise(eatsAnApple));
        then:
        testPassed()
        and:
        testOutcomeContainsStep("Eddie eats an apple")
    }


    def testPassed() {
        listener.latestTestOutcome().get().result == TestResult.SUCCESS
    }

    def testOutcomeContainsStep(String expectedDescription) {
        listener.latestTestOutcome().get().testSteps.find {
            step ->
                step.description == expectedDescription}
    }

    def testOutcomeContainsNoSteps() {
        listener.latestTestOutcome().get().testSteps.isEmpty()
    }
}