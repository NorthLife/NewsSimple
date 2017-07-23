package learn.com.newssimple.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zyx on 2017/7/13.
 */

public class LogisticInf {

    private int resultcode;
    private String reason;
    private Result Result;
    @SerializedName("error_code")
    private int errorcode;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return Result;
    }

    public void setResult(Result Result) {
        this.Result = Result;
    }
}
