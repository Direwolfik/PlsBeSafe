package cz.jn91.plsbesafe;

/**
 * Created by jn91 on 12.11.2015.
 */
public class TestResult {

    String explanation;
    Result result;
    String name;

    public enum Result {
        OK,FAIL,NOT_TESTED,READY,IN_PROGRESS
    }

    public TestResult(String explanation,Result result,String name){
        this.explanation = explanation;
        this.result = result;
        this.name = name;
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
}
