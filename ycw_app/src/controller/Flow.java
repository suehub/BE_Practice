package controller;

import java.util.Arrays;
import java.util.function.Supplier;

public enum Flow {
    RUN(() -> "run"),
    STOP(() -> "stop"),
    CONTINUE(() -> "continue"),
    GUEST(() -> "guest"),
    NEW_GUEST(() -> "non_signed_guest"),
    OLD_GUEST(() -> "signed_guest");


    private final Supplier<String> flow;

    Flow(Supplier<String> flow) {
        this.flow = flow;
    }

    public String getFlow() {
        return flow.get();
    }
}
