package co.com.csanvel.api;

public class OrchestratorException extends RuntimeException {

    private static final long serialVersion = 1L;
    private String code;

    public OrchestratorException(String code, String message) {
        super(message);
        this.code = code;

    }
}

