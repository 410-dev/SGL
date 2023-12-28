package me.hysong.sgl;

import me.hysong.libhycore.CoreDate;

public class SGLSettings {

    public static final int RUN_MODE_DEVELOPMENT = 0;
    public static final int RUN_MODE_PRODUCTION = Integer.MAX_VALUE;
    public static int runMode = RUN_MODE_PRODUCTION;

    public static boolean enableVerbose = false;
    public static String logFormat = "[%time%] [%level%] - %trace.line%@%trace.class%.%trace.method%: %message%";
    public static LogLevel verboseLevel = LogLevel.SILENT;

    public enum LogLevel {
        SILENT,
        INFO,
        WARNING,
        ERROR,
    }

    public static void log(String message, LogLevel level) {
        if (level.ordinal() <= verboseLevel.ordinal()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String callerClassName = stackTrace[3].getClassName();
            String callerMethodName = stackTrace[3].getMethodName();
            int callerLineNumber = stackTrace[3].getLineNumber();
            String time = CoreDate.timestamp();
            message = logFormat
                    .replace("%time%", time)
                    .replace("%level%", level.name())
                    .replace("%trace.class%", callerClassName)
                    .replace("%trace.method%", callerMethodName)
                    .replace("%trace.line%", String.valueOf(callerLineNumber))
                    .replace("%message%", message);

            if (level == LogLevel.ERROR) {
                System.err.println(message);
            } else {
                System.out.println(message);
            }
        }
    }

    public static void runIf(int mode, Runnable runnable) {
        if (runMode == mode) {
            runnable.run();
        }
    }

    public static void runIf(int mode, Runnable runnable, Runnable elseRunnable) {
        if (runMode == mode) {
            runnable.run();
        } else {
            elseRunnable.run();
        }
    }

    public static void runCompared(int mode, Runnable runnable) {
        if (runMode >= mode) {
            runnable.run();
        }
    }

    public static boolean isRunnableSetting(int mode) {
        return runMode >= mode;
    }
}
