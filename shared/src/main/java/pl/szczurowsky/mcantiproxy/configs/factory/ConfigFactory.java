package pl.szczurowsky.mcantiproxy.configs.factory;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.configurer.Configurer;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.commons.SerdesCommons;

import java.io.File;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ConfigFactory {

    private final File dataFile;
    private final Supplier<Configurer> configurerSupplier;

    public ConfigFactory(File dataFile, Supplier<Configurer> configurerSupplier) {
        this.dataFile = dataFile;
        this.configurerSupplier = configurerSupplier;
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> type, String fileName, OkaeriSerdesPack... serdesPacks) {
        return this.produceConfig(type, new File(this.dataFile, fileName), serdesPacks);
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> type, File file, OkaeriSerdesPack... serdesPacks) {
        return ConfigManager.create(type, initializer -> initializer
                .withConfigurer(this.configurerSupplier.get(), Stream.concat(Stream.of(new SerdesCommons()), Arrays.stream(serdesPacks))
                        .toArray(OkaeriSerdesPack[]::new))
                .withBindFile(file)
                .saveDefaults()
                .load(true));
    }
}
