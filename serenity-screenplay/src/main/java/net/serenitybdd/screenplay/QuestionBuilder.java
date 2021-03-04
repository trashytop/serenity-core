package net.serenitybdd.screenplay;

import net.serenitybdd.screenplay.questions.QuestionWithDefinedSubject;

public class QuestionBuilder {
    private final String subject;

    QuestionBuilder(String subject) {
        this.subject = subject;
    }

    public <T> Question<T> answeredBy(Question<T> questionToAsk) {
        return new QuestionWithDefinedSubject<>(questionToAsk, subject);
    }
}