package page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.SysAdminOperation.Login;

public final class Result {

    private boolean success;
    private RuntimeException exception = null;
    private String eMessage = null;
    private Long adminId = null;

    public Result() {
        this.success = true;
    }

    public Result setFailure(RuntimeException exception, String eMessage) {
        this.success = false;
        this.exception = exception;
        this.eMessage = eMessage;
        return this;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
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

    public Long adminId() {
        return this.adminId;
    }
}
