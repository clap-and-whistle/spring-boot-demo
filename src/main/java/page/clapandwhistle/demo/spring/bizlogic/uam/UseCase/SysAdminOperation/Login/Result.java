package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.AdminUser.AdminUser;

public final class Result {

    private boolean success;
    private RuntimeException exception = null;
    private String eMessage = null;
    private AdminUser adminUserAggregate = null;

    public Result() {
        this.success = true;
    }

    public Result setFailure(RuntimeException exception, String eMessage) {
        this.success = false;
        this.exception = exception;
        this.eMessage = eMessage;
        return this;
    }

    public Result setAdminUser(AdminUser adminUser) {
        this.adminUserAggregate = adminUser;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public RuntimeException exception() {
        return this.exception;
    }

    public String eMessage() {
        return this.eMessage == null
                ? ""
                : this.eMessage;
    }

    public AdminUser adminUserAggregate() {
        return this.adminUserAggregate;
    }
}
