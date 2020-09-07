package de.kevtvkevin.dungeons;

import de.kevtvkevin.dungeons.commands.RegisterDungeonCommand;
import de.kevtvkevin.dungeons.configs.DungeonConfigs;
import de.kevtvkevin.dungeons.helper.LoadSchematics;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dungeons extends JavaPlugin {

    public static Dungeons instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);
        System.out.println("Started");
        this.createConfigs();
        this.registerCommands();
        this.registerListener();
        //LoadSchematics.loadSchematic();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Stopped");
    }

    private void registerCommands() {
        this.getCommand("register").setExecutor(new RegisterDungeonCommand());
    }

    private void createConfigs() {
        DungeonConfigs.setConfigs();
    }

    private void registerListener() {

    }

    public static Dungeons getInstance() {
        return instance;
    }

    public static void setInstance(Dungeons instance) {
        Dungeons.instance = instance;
    }
}
