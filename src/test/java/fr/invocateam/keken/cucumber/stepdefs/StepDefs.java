package fr.invocateam.keken.cucumber.stepdefs;

import fr.invocateam.keken.KekenApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = KekenApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
