package br.com.guilhermealvessilveira.wifi.wireless802n.manager;

import java.util.logging.Logger;

public class ThreadUtils {

    private static final Logger LOG = Logger.getLogger(ThreadUtils.class.getName());

    private ThreadUtils() {
        throw new IllegalArgumentException("No ThreadUtils");
    }

    public static void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ex) {
            LOG.severe(String.format("Exception: %s", ex.getMessage()));
        }
    }
}
