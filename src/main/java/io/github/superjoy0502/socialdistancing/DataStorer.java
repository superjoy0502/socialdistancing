package io.github.superjoy0502.socialdistancing;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author superjoy0502
 */

public class DataStorer {
    private final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
    private final SocialDistancingPlugin plugin = (SocialDistancingPlugin) pluginManager.getPlugin("SocialDistancing");

    File virusFile = new File(plugin.getDataFolder(), "virusMap.yml");
    FileConfiguration virusConfig = YamlConfiguration.loadConfiguration(virusFile);

    public void saveCustomYml(File ymlFile, FileConfiguration ymlConfig) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeVirusMap(LinkedHashMap<UUID, Boolean> map) {
        virusConfig.set("virusMap", map);
        saveCustomYml(virusFile, virusConfig);
    }

    @Nullable
    public LinkedHashMap<UUID, Boolean> getVirusMap() {
        return (LinkedHashMap<UUID, Boolean>) virusConfig.get("virusMap");
    }
}
