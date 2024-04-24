package controller;

import java.util.ArrayList;
import java.util.function.Supplier;

public enum Message {
    ERROR(() -> "[Error]"),
    ERROR_WRONG_INPUT(() -> "[Error] 화면에 표시된 번호만 입력해주세요!"),
    ERROR_EXIST_USER(() -> "[Error] 이미 가입된 회원입니다!"),
    ERROR_LOGIN_FAILED(() -> "[Error] ID와 비밀번호를 확인하세요!"),
    INFO(() -> "[Info]"),
    INFO_EXCUTE_LOGIN(() -> "[Info] 로그인을 진행합니다."),
    INFO_EXCUTE_SIGNUP(() -> "[Info] 회원가입을 진행합니다."),
    INFO_SUCCESS_LOGIN(() -> "[Info] ? 님 환영합니다!"),
    INFO_EXCUTE_OPEN_ACCOUNT(() -> "[Info] ? 상품 가입을 진행합니다."),
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

    // "[Info] level: ?인 ? 님 ?에 오신걸 환영합니다."

    public String getMessage(ArrayList<String> variables) {
        String old_text = message.get();
        String new_text = "";

        int searchIdx = 0, variableIdx = 0;
        for (int i = 0; i < old_text.length(); i++) {
            int search_result = old_text.indexOf("?",searchIdx);
            if (search_result > -1) {
                new_text = old_text.substring(0,search_result)
                           + variables.get(variableIdx)
                           + old_text.substring(search_result+1,-1);
                searchIdx = search_result+1;
                variableIdx++;
                i = search_result;
            } else if (search_result == -1){
                break;
            }
        }

        return new_text;
    }

}
