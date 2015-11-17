package cz.jn91.plsbesafe;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Class containing the test status of given test
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class TestResult implements Serializable {

    private static final long serialVersionUID = 0L;

    String explanation;
    Status status;
    String name;
    TestResolver resolver;

    /**
     * Enum that is used to represent the current status of test.
     */
    public enum Status {
        OK, FAIL, NOT_TESTED, READY, IN_PROGRESS
    }

    /**
     * Creates new Test Status
     *
     * @param explanation String containing the explanation of test
     * @param status      status of test - initial value should probably be Status.READY
     * @param name        String containing the name of test
     * @param resolver    Instance of TestResolver that handles the results of this test
     */
    public TestResult(String explanation, Status status, String name, TestResolver resolver) {
        this.explanation = explanation;
        this.status = status;
        this.name = name;
        this.resolver = resolver;
    }

    /**
     * Get the status of this test
     *
     * @return Status of test
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the status of this test
     *
     * @param status current status of the test
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the explanation for this test case
     *
     * @return String explanation
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Returns name of the test case
     *
     * @return String with name of test case
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of test
     *
     * @param name String that is displayed for this test
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns TestResolver for this test case
     *
     * @return TestResolver instance
     */
    public TestResolver getResolver() {
        return resolver;
    }


    /*
     * Provides the API for resolving problems with given test
     */
    public interface TestResolver {
        /**
         * tries to resolve the problem - e.g. by opening the settings
         *
         * @param activity activity which handles the resolution
         */
        public void resolveProblem(Activity activity);

        /**
         * returns the menu icon used for resolve button
         *
         * @param activity activity activity which handles the resolution
         * @return Drawable for menu icon
         */
        public Drawable getMenuIcon(Activity activity);
    }
}
