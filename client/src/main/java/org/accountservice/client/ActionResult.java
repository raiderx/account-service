package org.accountservice.client;

/**
 * @author Pavel Karpukhin
 */
public class ActionResult<T> {

    private T data;
    private boolean success;
    private String message;

    public ActionResult(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public ActionResult() {
    }

    public static <T> ActionResult<T> succeed() {
        return new ActionResult(null, true, null);
    }

    public static <T> ActionResult<T> succeed(T data) {
        return new ActionResult(data, true, null);
    }

    public static <T> ActionResult<T> failed(String message) {
        return new ActionResult(null, false, message);
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
