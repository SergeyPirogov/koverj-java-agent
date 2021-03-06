package io.koverj.testng.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.koverj.agent.selenide.KoverjSelenide;
import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.koverj.testng.tests.ToDoAngular.*;

public class ToDoTestAngular {

    static {
        SelenideLogger.addListener("LocatorEventsListener", new KoverjSelenide());
    }

    @BeforeClass
    static void setUp() {
        Configuration.baseUrl = "http://todomvc.com/examples/typescript-angular";
//        Configuration.browser = "tests.old.RemoteChromeDriverProvider";
        Configuration.startMaximized = true;

        Selenide.open("about:blank");
    }

    private void open() {
        Selenide.open("/");
    }

    @Test
    void testCanOpenDifferenturl() {
        open();
        addTodo("fromMain");
        Selenide.open("#/completed");
        addTodo("fromCompleted");
        Selenide.open("#/active");
        addTodo("fromActive");
    }

    @Test
    public void testCanDestroyTodo() {
        open();
        addTodo("toDestroy");
        destroyTodo();
    }

    @Test
    public void testCanNotAddEmptyTodo() {
        Selenide.open("/");
        addTodo(Keys.ENTER);
    }

    @Test
    void testCanAddTodo() {
        open();
        addTodo("demo");
    }

    @Test
    void testCanToggleAll() {
        open();
        addTodo("2");
        addTodo("1");
        toggleAll();
        $("body > section > footer > span > strong").shouldHave(Condition.text("0"));
    }

    @Test
    void testCanEditTodo() {
        open();
        addTodo("edit");
        editTodo("edit", "demo", Keys.ENTER);
    }

    @Test
    void testCanCancelEditTodo() {
        open();
        addTodo("AtoВeEdit");
        switchToActive();
        editTodo("AtoВeEdit", "demo", Keys.ESCAPE);
    }

    @Test
    void testTodoDeletedIfEmptyName() {
        open();
        addTodo("toEditForEmpty");
        editTodo("toEditForEmpty", Keys.BACK_SPACE, Keys.ENTER);
    }

    @Test
    public void testCanSwitchActiveAndCompleted() {
        open();
        addTodo("toEdit");
        $("body > section > footer > ul > li:nth-child(2) > a").click();
        $("body > section > footer > ul > li:nth-child(3) > a").click();
        $("body > section > footer > ul > li:nth-child(1) > a").click();
    }

    @Test
    void testCanClearCompletedTodo() {
        open();
        addTodo("toEdit");
        addTodo("345");
        toggleAll();
        clearCompleted();
    }
}

class ToDoAngular {


    public static void addTodo(String text) {
        $("body > section > header > form > input").setValue(text).pressEnter();
    }

    public static void addTodo(Keys keys) {
        $("body > section > header > form > input").sendKeys(keys);
        $("body > section > header > form > input").sendKeys(Keys.ENTER);
    }

    public static void destroyTodo() {
        $("body > section > section > ul > li > div > label").hover();
        $("body > section > section > ul > li > div > button").click();
    }

    public static void toggleAll() {
        $("body > section > section > label").click();
    }

    public static void editTodo(String oldText, String newText, Keys keys) {
        List<String> text = $$("body > section > section > ul > li > div > label").stream().map(it -> it.getText()).collect(Collectors.toList());

        $$("body > section > section > ul > li > div > label")
                .findBy(Condition.exactText(oldText)).doubleClick();

        int i = text.indexOf(oldText) + 1;

        $("body > section > section > ul > li:nth-child(" + i + ") > form > input").sendKeys(newText + keys);
    }

    public static void editTodo(String oldText, Keys... keys) {
        List<String> text = $$("body > section > section > ul > li > div > label").stream().map(it -> it.getText()).collect(Collectors.toList());
        int index = text.indexOf(oldText) + 1;
        $$("body > section > section > ul > li > div > label")
                .findBy(Condition.exactText(oldText)).doubleClick();
        for (Keys key : keys) {
            if (key.equals(Keys.BACK_SPACE)) {
                for (int i = 0; i < oldText.length(); i++) {
                    $("body > section > section > ul > li:nth-child(" + index + ") > form > input").sendKeys(key);
                }
            } else {
                $("body > section > section > ul > li:nth-child(" + index + ") > form > input").sendKeys(key);
            }
        }
    }

    public static void switchToActive() {
        $("body > section > footer > ul > li:nth-child(2) > a").click();
    }

    public static void clearCompleted() {
        $("body > section > footer > button").click();
    }
}
