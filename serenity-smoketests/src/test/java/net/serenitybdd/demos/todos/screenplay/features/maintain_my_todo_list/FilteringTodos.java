package net.serenitybdd.demos.todos.screenplay.features.maintain_my_todo_list;

import net.serenitybdd.demos.todos.screenplay.questions.CurrentFilter;
import net.serenitybdd.demos.todos.screenplay.questions.TheItems;
import net.serenitybdd.demos.todos.screenplay.tasks.CompleteItem;
import net.serenitybdd.demos.todos.screenplay.tasks.FilterItems;
import net.serenitybdd.demos.todos.screenplay.tasks.Start;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.demos.todos.screenplay.model.TodoStatusFilter.*;
import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
@WithTags({
        @WithTag("Screenplay pattern"),
        @WithTag("version:RELEASE-2"),
})
public class FilteringTodos {

    private Actor james = Actor.named("James");
    @Managed private WebDriver hisBrowser;
    @Before public void jamesCanBrowseTheWeb() {
        james.can(BrowseTheWeb.with(hisBrowser));
    }

    @Test
    public void should_be_able_to_view_only_completed_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
            CompleteItem.called("Walk the dog"),
            FilterItems.toShow(Completed)
        );

        then(james).should(seeThat(TheItems.displayed(), hasItems("Walk the dog")));
        and(james).should(seeThat(TheItems.displayed(), not(hasItems("Put out the garbage"))));
        and(james).should(seeThat(CurrentFilter.selected(), is(Completed)));
    }

    @Test
    public void should_be_able_to_view_only_incomplete_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
            CompleteItem.called("Walk the dog"),
            FilterItems.toShow(Active)
        );

        then(james).should(seeThat(TheItems.displayed(), hasItems("Put out the garbage")));
        and(james).should(seeThat(TheItems.displayed(), not(hasItems("Walk the dog"))));
        and(james).should(seeThat(CurrentFilter.selected(), is(Active)));
    }

    @Test
    public void should_be_able_to_view_both_complete_and_incomplete_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
            CompleteItem.called("Walk the dog"),
            FilterItems.toShow(Active),
            FilterItems.toShow(All)
        );

        then(james).should(seeThat(TheItems.displayed(), hasItems("Walk the dog", "Put out the garbage")));
        and(james).should(seeThat(CurrentFilter.selected(), is(All)));
    }
}