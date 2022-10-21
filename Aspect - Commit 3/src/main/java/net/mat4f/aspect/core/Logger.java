package net.mat4f.aspect.core;

import static net.mat4f.aspect.Aspect.*;

public class Logger {
    public static int FATALS_ENABLED = ASPECT_TRUE, ERRORS_ENABLED = ASPECT_TRUE, WARNINGS_ENABLED = ASPECT_TRUE, INFOS_ENABLED = ASPECT_TRUE, DEBUG_ENABLED = ASPECT_TRUE, TRACES_ENABLED = ASPECT_TRUE;
    public static int FATAL = 0, ERROR = 1, WARNING = 2, INFO = 3, DEBUG = 4, TRACE = 5;
    public static String[] LOG_PREFIXES = {
        "[ FATAL ]", "[ ERROR ]", "[ WARNING ]", "[ INFO ]", "[ DEBUG ]", "[ TRACE ]"
    };

    private static void asLogOutput(Integer log_level, String message) {
        System.out.println(LOG_PREFIXES[log_level] + " " + message);
    }
    private static void asErrOutput(Integer log_level, String message) {
        System.err.println(LOG_PREFIXES[log_level] + " " + message);
    }

    public static void asFatal(String message)    {
        if (FATALS_ENABLED != ASPECT_TRUE) return;
        asErrOutput(FATAL, message);
    }
    public static void asError(String message)    {
        if (ERRORS_ENABLED != ASPECT_TRUE) return;
        asErrOutput(ERROR, message);
    }
    public static void asWarning(String message)  {
        if (WARNINGS_ENABLED != ASPECT_TRUE) return;
        asLogOutput(WARNING, message);
    }
    public static void asInfo(String message)     {
        if (INFOS_ENABLED != ASPECT_TRUE) return;
        asLogOutput(INFO, message);
    }
    public static void asDebug(String message)    {
        if (DEBUG_ENABLED != ASPECT_TRUE) return;
        asLogOutput(DEBUG, message);
    }
    public static void asTrace(String message)    {
        if (TRACES_ENABLED != ASPECT_TRUE) return;
        asLogOutput(TRACE, message);
    }

}
