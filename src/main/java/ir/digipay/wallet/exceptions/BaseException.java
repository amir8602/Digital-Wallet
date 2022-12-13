package ir.digipay.wallet.exceptions;

public class BaseException extends RuntimeException{

    private final String exceptionType;

    public BaseException(String exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }



    public BaseException(String message, String exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public BaseException(String message, Throwable cause, String exceptionType) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }

    public BaseException(Throwable cause, String exceptionType) {
        super(cause);
        this.exceptionType = exceptionType;
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String exceptionType) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionType = exceptionType;
    }

    public String getExceptionType() {
        return exceptionType;
    }
}
