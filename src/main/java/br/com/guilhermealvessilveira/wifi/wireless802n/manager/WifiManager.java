package br.com.guilhermealvessilveira.wifi.wireless802n.manager;

import java.util.Objects;
import java.util.logging.Logger;

import static br.com.guilhermealvessilveira.wifi.wireless802n.manager.WindowsProgramsPathUtils.*;

public class WifiManager {

    private static final long TIMEOUT_SLEEP = 5000L;
    private static final long TIMEOUT_INTERVAL = 2000L;
    private static final String DISCONNECTED_MSG = "Ping request could not find host www.google.com";
    private static final String REQUEST_TIMEOUT = "Request timed out";
    private static final Logger LOG = Logger.getLogger(WifiManager.class.getName());

    private final String ssid;

    public WifiManager(final String ssid) {
        this.ssid = Objects.requireNonNull(ssid);
    }

    public void ensureConnection() {

        while (true) {
            final var manager = new ProcessManager(
                    "ping",
                    getPing(),
                    "www.google.com"
            );

            manager.execute(line -> {
                LOG.info(line);
                if (line.contains(DISCONNECTED_MSG)
                    || line.contains(REQUEST_TIMEOUT)) {
                    disconnect();
                    connect();
                    ThreadUtils.sleep(TIMEOUT_INTERVAL);
                }
            });

            ThreadUtils.sleep(TIMEOUT_SLEEP);
        }
    }

    private void disconnect() {

        final var manager = new ProcessManager(
                "disconnect",
                getCmd(),
                "/c",
                String.format("%s wlan disconnect", getNetsh())
        );
        manager.execute();
    }

    private void connect() {

        final var manager = new ProcessManager(
                "connect",
                getCmd(),
                "/c",
                String.format("%s wlan connect ssid=%s name=%s", getNetsh(), ssid, ssid)
        );
        manager.execute();
    }
}
