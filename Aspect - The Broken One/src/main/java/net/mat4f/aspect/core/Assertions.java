package net.mat4f.aspect.core;

import static net.mat4f.aspect.Aspect.*;
import static net.mat4f.aspect.core.Logger.*;

public class Assertions {
    public static int ASSERTIONS_ENABLED = ASPECT_TRUE;

    public static void asAssertion_failure(String cause, StackTraceElement[] trace, boolean fatal) {
        if (ASSERTIONS_ENABLED != ASPECT_TRUE) return;
        if (fatal) {
            if (cause != ASPECT_NONE) asFatal("Critical Assertion Failed: " + cause + "!" + getStackTrace("\r\n\tat ", trace));
            else asFatal("Critical Assertion Failed!" + getStackTrace("\r\n\tat ", trace));
            System.exit(-1);
        } else {
            if (cause != ASPECT_NONE) asError("Critical Assertion Failed: " + cause + "!" + getStackTrace("\r\n\tat ", trace));
            else asError("Critical Assertion Failed!" + getStackTrace("\r\n\tat ", trace));
        }
    }

    public static String getStackTrace(String linePrefix, StackTraceElement[] stackTrace) {
        StringBuilder result = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            result.append(linePrefix).append(element.toString());
        }
        return result.toString();
    }

    public static boolean asAssert(boolean expression, String reason) {
        if (expression) return true;
        asAssertion_failure(reason, Thread.currentThread().getStackTrace(), false);
        return false;
    }

    public static boolean asAssertFatal(boolean expression, String reason) {
        if (expression) return true;
        asAssertion_failure(reason, Thread.currentThread().getStackTrace(), true);
        return false;
    }

    public static boolean asAssertNotNull(Object object, String reason) {
        if (object != null) return true;
        asAssertion_failure(reason, Thread.currentThread().getStackTrace(), false);
        return false;
    }

    public static boolean asAssertNotNullFatal(Object object, String reason) {
        if (object != null) return true;
        asAssertion_failure(reason, Thread.currentThread().getStackTrace(), true);
        return false;
    }
}
