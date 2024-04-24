package controller;

import java.util.function.Supplier;

public enum Tag {
    MAIN(() -> "main"),
    LOG_IN(() -> "log_in"),
    SIGN_UP(() -> "sign_up"),
    MY_PAGE(() -> "my_page"),
    MANAGE_ACCOUNTS(() -> "manage_accounts"),
    OPEN_ACCOUNT(() -> "open_account"),
    CLOSE_ACCOUNT(() -> "close_account"),
    ACCOUNT_HISTORY(() -> "account_history"),
    DEPOSIT(() -> "deposit, 입금"),
    WITHDRAW(() -> "withdraw, 출금"),
    TRANSFER(() -> "transfer, 송금"),
    // Tags for Trade.action field
    //ACTION_DEPOSIT(() -> )

    // Tags for Status.data field
    MY_ACCOUNT(() -> "my_account"),
    OTHER_ACCOUNT(() -> "other_account"),
    DEPOSIT_ACCOUNT(() -> "예금"),
    SAVINGS_ACCOUNT(() -> "적금"),
    CACHE_MANAGEMENT_ACCOUNT(() -> "CMA");

    private final Supplier<String> tag;

    Tag(Supplier<String> tag) {
        this.tag = tag;
    }

    public String getTag(){
        return tag.get();
    }

}
