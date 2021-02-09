package org.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "stepdefs",
//        tags = "@ts05",
        plugin = {
                "pretty",
                "html:target/cucumber/report.html",
                "json:target/cucumber/report.json"
        })
public class TestRunner {
}
