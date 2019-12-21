package br.com.guilhermealvessilveira.wifi.wireless802n.manager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

public  class StreamGobbler implements Runnable {

    private static final Logger LOG = Logger.getLogger(StreamGobbler.class.getName());

    private final InputStream inputStream;
    private final Consumer<String> consumer;

    public StreamGobbler(
            final InputStream inputStream,
            final Consumer<String> consumer
    ) {
        this.inputStream = Objects.requireNonNull(inputStream);
        this.consumer = Objects.requireNonNull(consumer);
    }

    @Override
    public void run() {
        try (final var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines()
                    .forEach(consumer);
        } catch (final Exception ex) {
            LOG.severe(String.format("Exception: %s", ex.getMessage()));
        }
    }
}