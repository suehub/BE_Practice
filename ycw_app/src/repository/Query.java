package repository;

import java.util.function.Supplier;


public enum Query {
    USER_LOG_IN("log_in", () -> "select user_id, name from Users where user_id = ? and password = ?;"),
    USER_SELECT("my_page", () -> "select user_id, name from Users where user_id = ?;"),
    USER_INSERT("sign_up", () -> "insert into Users values(?,?,?);"),
    ACCOUNT_SELECT_MULTIPLE("manage_accounts", () -> "select account_number, Users_user_id, product_type, balance " +
                                                     "from Accounts where Users_user_id = ?;"),
    ACCOUNT_SELECT("select_account", () -> "select account_number, Users_user_id,product_type, balance  \n" +
                                                "from practice_db.Accounts \n" +
                                                "where account_number = ?;"),
    ACCOUNT_INSERT("open_account", () -> "insert into Accounts values(?,?,?,?);"),
    ACCOUNT_DELETE("close_account", () -> "delete from Accounts where account_number = ?;"),
    ACCOUNT_UPDATE("trade", () -> "update Accounts set balance = ? where account_number = ?;"),
    TRADE_INSERT("insert_trade", () -> "insert into Trades values(nextval('TRADE_SEQ'),?,?,?,?,?,?);"),
    TRADE_SELECT_MUTIPLE("trade_history", () -> "select trade_id, action, request_account, target_account, amount from Trades " +
                                "where request_account = ? or target_account = ? " +
                                "order by trade_id;");


    private final String queryName;
    private final Supplier<String> queryString;

    Query(String key, Supplier<String> queryString) {
        this.queryName = key;
        this.queryString = queryString;
    }


    public String getQueryString(){
        return queryString.get();
    }

}
