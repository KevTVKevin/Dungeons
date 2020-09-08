package de.kevtvkevin.dungeons.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.Player;
import de.kevtvkevin.dungeons.helper.LoadSchematics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GenerateDungeonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("generate")) {
            if(args.length >= 1) {
                System.out.println(LoadSchematics.exists(args[0]));
                if(LoadSchematics.exists(args[0]) != null) {
                    Player player = BukkitAdapter.adapt((org.bukkit.entity.Player) sender);
                    LoadSchematics.loadSchematic(LoadSchematics.exists(args[0]), player);
                }
            }
        }
        return false;
    }
}
