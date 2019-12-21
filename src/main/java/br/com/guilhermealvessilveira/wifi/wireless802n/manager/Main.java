package br.com.guilhermealvessilveira.wifi.wireless802n.manager;

import java.util.Arrays;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        final var ssid = Arrays.stream(args)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("You didn't pass the Wi-fi SSID!"));

        LOG.info("WI-FI Manager started!");
        final var wifi = new WifiManager(ssid);
        wifi.ensureConnection();
    }
}
