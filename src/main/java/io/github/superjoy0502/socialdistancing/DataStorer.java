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
    private SocialDistancingPlugin plugin;

    public DataStorer(SocialDistancingPlugin plugin){
        this.plugin = plugin;
    }

    File virusFile = new File(plugin.getDataFolder(), "virusMap.yml");
    FileConfiguration virusConfig = YamlConfiguration.loadConfiguration(virusFile);

    public void onStartUp(SocialDistancingPlugin plugin){
        this.plugin = plugin;
        if (!virusFile.exists()){
            plugin.saveResource("virusMap.yml", false);
        }
    }

    public FileConfiguration getVirusConfig(){
        return virusConfig;
    }

    public File getVirusFile(){
        return virusFile;
    }
}
