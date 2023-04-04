package SingVersion.FitnesApp.core.exception;

public class SingleErrorResponse extends RuntimeException {

    private String logref;
    private String message;

    public SingleErrorResponse(String message) {
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public void setLogref(String logref) {
        this.logref = logref;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
