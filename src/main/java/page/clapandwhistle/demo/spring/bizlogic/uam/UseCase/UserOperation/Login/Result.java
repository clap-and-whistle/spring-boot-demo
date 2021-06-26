package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.Login;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.User;

final public class Result {
    private boolean success;
    private RuntimeException exception = null;
    private String eMessage = null;
    private User userAggregate = null;

    public Result() {
        this.success = true;
    }

    public Result setFailure(RuntimeException exception, String eMessage) {
        this.success = false;
        this.exception = exception;
        this.eMessage = eMessage;
        return this;
    }

    public Result setUser(User userAggregate) {
        this.userAggregate = userAggregate;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public RuntimeException exception() {
        return this.exception;
    }

    public String eMessage() {
        return this.eMessage == null
                ? ""
                : this.eMessage;
    }

    public User userAggregate() {
        return this.userAggregate;
    }
}
