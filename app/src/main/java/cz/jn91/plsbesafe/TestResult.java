package cz.jn91.plsbesafe;

import android.app.Activity;
import android.graphics.drawable.Drawable;

/**
 * Class containing the test status of given test
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class TestResult  {
    String explanation;
    Status status;
    String name;
    String positiveName;
    String negativeName;
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
    public TestResult(String explanation, Status status, String name, String positiveName, String negativeName, TestResolver resolver) {
        this.explanation = explanation;
        this.status = status;
        this.name = name;
        this.positiveName = positiveName;
        this.negativeName = negativeName;
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

    /**
     * Returns the String representing the positive outcome of the test
     *
     * @return String that is displayed for positive result
     */
    public String getPositiveName() {
        return positiveName;
    }

    /**
     * Returns the String representing the negative outcome of the test
     *
     * @return String that is displayed for negative result
     */
    public String getNegativeName() {
        return negativeName;
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
