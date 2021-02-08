package org.example;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WebDriverManager {

    private static final Logger logger = LogManager.getLogger();
    public static final String PROPERTIES_PATH = "src/test/resources/config.properties";
    private WebDriver driver;
    private String url;

    public WebDriver getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public WebDriver setUpDriver() {
        logger.info("Properties file located at: {}", PROPERTIES_PATH);
        Properties properties = getProperties(PROPERTIES_PATH);
//        url = properties.getProperty("url");
        url = System.getProperty("url") != null ? System.getProperty("url") : properties.getProperty("url") ;
//        long timeout = Long.parseLong(properties.getProperty("timeout.in.seconds"));
        long timeout = System.getProperty("timeout") != null ? Long.parseLong(System.getProperty("timeout")) : Long.parseLong(properties.getProperty("timeout.in.seconds"));
        boolean maximizeWindow = Boolean.parseBoolean(properties.getProperty("maximize.window"));
        driver = selectWebDriver(properties);
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        logger.info("Implicitly wait timeout set to {} seconds", timeout);
        driver.manage().deleteAllCookies();
        logger.info("All cookies deleted");
        if (maximizeWindow) {
            driver.manage().window().maximize();
        }
        return driver;
    }

    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String testName = scenario.getName();
            scenario.attach(screenshot, "image/png", testName);
        }
        driver.quit();
    }

    public Properties getProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }

    private WebDriver selectWebDriver(Properties properties) {
        String browser = System.getProperty("browser");
        if (browser == null) {
            browser = properties.getProperty("browser");
        }
        String chromeDriverLocation = properties.getProperty("chrome.driver.location");
        String firefoxDriverLocation = properties.getProperty("firefox.driver.location");
        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", firefoxDriverLocation);
                driver = new FirefoxDriver();
                break;
            default:
                logger.warn("Browser provided [{}] is either empty or incorrect. Proceeding with Firefox...", browser);
                System.setProperty("webdriver.gecko.driver", firefoxDriverLocation);
                driver = new FirefoxDriver();
                browser = "firefox";
                break;
        }
        logger.info("{} browser will be used for testing", browser);
        return driver;
    }
}
