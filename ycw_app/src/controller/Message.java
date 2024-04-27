package controller;

import java.util.ArrayList;
import java.util.function.Supplier;

public enum Message {
    ERROR(() -> "[Error]"),
    ERROR_WRONG_INPUT(() -> "[Error] 화면에 표시된 번호만 입력해주세요."),
    ERROR_WRONG_ACCOUNT(() -> "[Error] 올바른 계좌번호를 입력하세요."),
    ERROR_WRONG_REQUEST_ACCOUNT(() -> "[Error] 올바른 보낼 계좌번호를 입력하세요."),
    ERROR_WRONG_TARGET_ACCOUNT(() -> "[Error] 올바른 받는 계좌번호를 입력하세요."),
    ERROR_EXIST_USER(() -> "[Error] 이미 가입된 회원입니다."),
    ERROR_FAILED_LOGIN(() -> "[Error] ID와 비밀번호를 확인하세요."),
    ERROR_FAILED_TRADE(() -> "[Error] 거래에 실패하였습니다."),
    ERROR_FAILED_TRANSFER_OVER_BALANCE(() -> "[Error] ?액이 보유잔고를 초과합니다."),
    INFO(() -> "[Info]"),
    INFO_EXCUTE_LOGIN(() -> "[Info] 로그인을 진행합니다."),
    INFO_EXCUTE_SIGNUP(() -> "[Info] 회원가입을 진행합니다."),
    INFO_EXCUTE_OPEN_ACCOUNT(() -> "[Info] ? 상품 가입을 진행합니다."),
    INFO_EXCUTE_TRADE(() -> "[info] ?을 진행합니다."),
    INFO_SUCCESS_LOGIN(() -> "[Info] ?님 환영합니다!"),
    INFO_CANCLE_WORK(() -> "[Info] 작업을 취소합니다."),
    INFO_CANCLE_TRADE(() -> "[Info] ?을 취소합니다."),
    INFO_CANCLE_OPEN_ACCOUNT(() -> "[Info] 상품 가입을 취소합니다."),
    INFO_CANCLE_CLOSE_ACCOUNT(() -> "[Info] 계좌 해지를 취소합니다."),
    CANCLE(() -> "취소");


    private final Supplier<String> message;

    Message(Supplier<String> message) {
        this.message = message;
    }

    public String getMessage(){
        return message.get();
    }

    public String getMessage(String variable){
        return message.get().replace("?", variable);
    }

    public String getMessage(ArrayList<String> variables) {
        /* ! 검증필요
        * Example
        * "[Info] level: ?인 ? 님 ?에 오신걸 환영합니다."
        * */
        String old_text = message.get();
        String new_text= "", last_text = "";

        int searchIdx = 0, variableIdx = 0;
        for (int i = 0; i < old_text.length(); i++) {
            int search_result = old_text.indexOf("?",searchIdx);
            if (search_result > -1) {
                new_text = old_text.substring(0,search_result)
                           + variables.get(variableIdx);
                last_text = old_text.substring(search_result+1,-1);
                searchIdx = search_result+1;
                variableIdx++;
                i = search_result;
            } else if (search_result == -1){
                new_text += last_text;
                break;
            }
        }

        return new_text;
    }

}
