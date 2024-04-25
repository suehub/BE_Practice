package src.Exception;

import java.util.logging.Logger;

public class GlobalException {
    private static final Logger LOGGER = Logger.getLogger(GlobalException.class.getName());
    public static void log(Exception e) {
        LOGGER.severe(e.getMessage());
    }
}
