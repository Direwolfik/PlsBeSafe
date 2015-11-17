package cz.jn91.plsbesafe;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

import cz.jn91.plsbesafe.testTasks.BaseTestAsyncTask;

/**
 * Created by jn91 on 12.11.2015.
 */
public class TestResult implements Serializable {

    private static final long serialVersionUID = 0L;

    String explanation;
    Result result;
    String name;
    TestResolver resolver;


    public enum Result {
        OK, FAIL, NOT_TESTED, READY, IN_PROGRESS
    }

    public TestResult(String explanation, Result result, String name,TestResolver resolver) {
        this.explanation = explanation;
        this.result = result;
        this.name = name;
        this.resolver = resolver;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestResolver getResolver() {
        return resolver;
    }

    public void setResolver(TestResolver resolver) {
        this.resolver = resolver;
    }

    /*
     * Provides the API for resolving problems with given test
     */
    public interface TestResolver {
        /**
         * tries to resolve the problem - e.g. by opening the settings
         */
        public void resolveProblem(Activity activity);

        /**
         * returns the menu icon used for resolve button
         * @return Drawable for menu icon
         */
        public Drawable getMenuIcon(Activity activity);
    }
}
