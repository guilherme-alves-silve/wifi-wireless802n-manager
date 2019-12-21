package br.com.guilhermealvessilveira.wifi.wireless802n.manager;

public class WindowsProgramsPathUtils {

    private WindowsProgramsPathUtils() {
        throw new IllegalArgumentException("No WindowsProgramsPathUtils!");
    }

    public static String getPing() {
        return getExecutableInWindowsSystem32("ping.exe");
    }

    public static String getCmd() {
        return getExecutableInWindowsSystem32("cmd.exe");
    }

    public static String getNetsh() {
        return getExecutableInWindowsSystem32("netsh.exe");
    }

    private static String getExecutableInWindowsSystem32(final String exe) {
        return String.format("C:/Windows/System32/%s", exe);
    }
}
