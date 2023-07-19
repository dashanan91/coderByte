package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.apache.log4j.BasicConfigurator;

@CucumberOptions(
            features = "src/main/feature_files/login.feature",
            glue={"StepDefinition"}
    )
    public class CucumberTestRunner extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = true)
    public Object[][] scenarios(){
            BasicConfigurator.configure();
            return super.scenarios();
        }
}
