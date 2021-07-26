package io.github.superjoy0502.socialdistancing;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author superjoy0502
 */

public class DataStorer {
    File virusFile;
    FileConfiguration virusConfig;

    public DataStorer(SocialDistancingPlugin plugin) {
        this.virusFile = new File(plugin.getDataFolder(), "virusMap.yml");
        if (!virusFile.exists()) {
            plugin.saveResource("virusMap.yml", false);
        }
        this.virusConfig = YamlConfiguration.loadConfiguration(virusFile);
    }

    public FileConfiguration getVirusConfig() {
        return virusConfig;
    }

    public File getVirusFile() {
        return virusFile;
    }
}
