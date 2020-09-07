package de.kevtvkevin.dungeons.configs;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.kevtvkevin.dungeons.Dungeons;
import de.kevtvkevin.dungeons.objects.Dungeon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DungeonConfigs {

    private static File configFile;
    private static FileConfiguration config;

    public static void setConfigs() {
        createConfigFile();
        config = Dungeons.getInstance().getConfig();
        config.set("configPath", "plugins//" + Dungeons.getInstance().getDescription().getName() + "//schematics");
        save();
        setDirectory();
    }

    private static void createConfigFile() {
        configFile = new File("plugins//" + Dungeons.getInstance().getDescription().getName(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        config.options().copyDefaults(true);
        save();
    }

    private static void setDirectory() {
        if(Dungeons.getInstance().getConfig().get("configPath") != null) {
            File createDir = new File(Objects.requireNonNull(config.getString("configPath")));
            createDir.mkdir();
        }
    }

    private static void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
