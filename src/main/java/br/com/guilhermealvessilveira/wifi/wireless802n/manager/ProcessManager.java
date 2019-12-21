package br.com.guilhermealvessilveira.wifi.wireless802n.manager;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ProcessManager {

    private static final int TIMEOUT = 30;
    private static final Logger LOG = Logger.getLogger(ProcessManager.class.getName());

    private final String action;
    private final String[] commands;

    public ProcessManager(
            final String action,
            final String... commands
    ) {
        this.action = Objects.requireNonNull(action);
        this.commands = Objects.requireNonNull(commands);
        if (commands.length == 0) {
            throw new IllegalArgumentException("commands array must be greater than 0!");
        }
    }

    public void execute() {
        execute(LOG::info);
    }

    public void execute(final Consumer<String> consumer) {
        final var executor = Executors.newSingleThreadExecutor();
        try {
            final var builder = new ProcessBuilder(commands);
            final var process = builder.start();

            final var streamGobbler =
                    new StreamGobbler(process.getInputStream(), consumer);
            final var streamGobblerError =
                    new StreamGobbler(process.getErrorStream(), consumer);

            executor.submit(streamGobbler);
            executor.submit(streamGobblerError);
            int exitCode = process.waitFor();

            LOG.info(String.format("Exit code of %s is: %d", action, exitCode));
        } catch (final Exception ex) {
            LOG.severe(String.format("Exception: %s", ex.getMessage()));
        } finally {
            try {
                executor.shutdown();
                executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
            } catch (final Exception ex) {
                LOG.severe(String.format("Exception: %s", ex.getMessage()));
            }
        }
    }
}
