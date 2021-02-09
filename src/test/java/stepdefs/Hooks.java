package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.WebDriverManager;

public class Hooks {

    private static final Logger logger = LogManager.getLogger();
    private WebDriverManager webDriverManager;

    public Hooks(WebDriverManager webDriverManager) {
        this.webDriverManager = webDriverManager;
    }

    @Before
    public void setUp(Scenario scenario) {
        String titleFormat = String.format(" SCENARIO: %s ", scenario.getName());
        String title = StringUtils.center(titleFormat, 65, '=');
        logger.info(title);
        webDriverManager.setUpDriver();
    }

    @After
    public void teardown(Scenario scenario) {
        webDriverManager.teardown(scenario);
    }
}
