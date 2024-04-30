package src.Exception;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class GlobalException {

//    1. logger 의 형식을 custom
//    2. 비밀번호를 암호화 or 입력 타입을 char[]로 제한하기
//    3.
//    */

    public static final Logger log = Logger.getLogger(GlobalException.class.getName());

    static {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers.length > 0) {
            for (Handler handler : handlers) {
                rootLogger.removeHandler(handler);
            }
        }

        Formatter formatter = new Formatter() {
            @Override
            public String format(LogRecord record) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(record.getMillis());

                StringBuffer buffer = new StringBuffer(1000);
                buffer.append("[발생시간] " + dateFormat.format(date));
                buffer.append(" [" + record.getSourceClassName() +" "+record.getSourceMethodName() + "] \n");
                buffer.append("["+ record.getLevel() +"] ");
                buffer.append(record.getMessage() + "\n");
                return buffer.toString(); // 포맷된 로그 메시지 반환
            }
        };

        Handler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        log.addHandler(handler);
    }

}
