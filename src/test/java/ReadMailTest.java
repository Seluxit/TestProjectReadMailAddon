
import com.seluxit.ReadEmail;
import io.testproject.java.sdk.v2.Runner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class ReadMailTest {

    private static Runner runner;

    @BeforeAll
    public static void setup() throws InstantiationException {
        runner = Runner.create("HNhM7J5EdPzffpiCLV8QP-7ml8jRZ7c6lEtro2DYLfE");
    }

    @AfterAll
    public static void tearDown() throws IOException {
        runner.close();
    }

    @Test
    public void runAction() throws Exception {

        // Create Action
        ReadEmail action = new ReadEmail();
        action.host = "";
        action.port = "";

        // Run action
        runner.run(action);
    }
}
