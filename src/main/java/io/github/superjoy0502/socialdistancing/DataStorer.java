package io.github.superjoy0502.socialdistancing;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author superjoy0502
 */

public class DataStorer {
    File virusFile;
    FileConfiguration virusConfig;

    public DataStorer(SocialDistancingPlugin plugin){
        this.virusFile = new File(plugin.getDataFolder(), "virusMap.yml");
        if (!virusFile.exists()){
            plugin.saveResource("virusMap.yml", false);
        }
        this.virusConfig = YamlConfiguration.loadConfiguration(virusFile);
    }

    public FileConfiguration getVirusConfig(){
        return virusConfig;
    }

    public File getVirusFile(){
        return virusFile;
    }
}
