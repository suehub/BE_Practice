package controller;

import java.util.function.Supplier;

public enum Tag {
    // Tags for controlling workflows
    MAIN(() -> "main"),
    LOG_IN(() -> "log_in"),
    SIGN_UP(() -> "sign_up"),
    MY_PAGE(() -> "my_page"),
    MANAGE_ACCOUNTS(() -> "manage_accounts"),
    OPEN_ACCOUNT(() -> "open_account"),
    CLOSE_ACCOUNT(() -> "close_account"),
    ACCOUNT_HISTORY(() -> "account_history"),
    DEPOSIT(() -> "deposit"),
    WITHDRAW(() -> "withdraw"),
    TRANSFER(() -> "transfer"),

    // Tags for Trade.action field
    DEFAULT_ACCOUNT(() -> "0000000000"),
    ACTION_DEPOSIT(() -> "입금"),
    ACTION_WITHDRAW(() -> "출금"),
    ACTION_TRANSFER(() -> "송금"),

    // Tags for Status Object
    DEFAULT_DATA(() -> ""),
    PUT_DATA(() -> "put_data"),
    UPDATE_DATA(() -> "update_data"),
    MY_ACCOUNT(() -> "my_account"),
    OTHER_ACCOUNT(() -> "other_account"),
    DEPOSIT_ACCOUNT(() -> "예금"),
    SAVINGS_ACCOUNT(() -> "적금"),
    CACHE_MANAGEMENT_ACCOUNT(() -> "CMA");


    private final Supplier<String> tag;

    Tag(Supplier<String> tag) {
        this.tag = tag;
    }

    Tag(String tag){
        this.tag = Tag.valueOf(tag).tag;
    }

    public String getTag(){
        return tag.get();
    }

}
