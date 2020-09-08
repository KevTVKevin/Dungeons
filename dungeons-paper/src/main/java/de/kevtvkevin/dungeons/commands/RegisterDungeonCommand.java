package de.kevtvkevin.dungeons.commands;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.scripting.CraftScriptContext;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import de.kevtvkevin.dungeons.Dungeons;
import de.kevtvkevin.dungeons.helper.LoadSchematics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class RegisterDungeonCommand implements CommandExecutor {

    private HashMap<Player, BlockVector3> pos1 = new HashMap<>();
    private HashMap<Player, BlockVector3> pos2 = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("register")) {
            Player player = (BukkitAdapter.adapt((org.bukkit.entity.Player) sender));
            org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player) sender;
            if(args.length >= 1) {
                switch (args[0]) {
                    case "pos1":
                        pos1.put(player, player.getLocation().toVector().toBlockPoint());
                        bukkitPlayer.sendMessage(player.getLocation().toVector().toBlockPoint().toString());
                        break;
                    case "pos2":
                        pos2.put(player, player.getLocation().toVector().toBlockPoint());
                        bukkitPlayer.sendMessage(player.getLocation().toVector().toBlockPoint().toString());
                        break;
                    case "save":
                        if(pos1.get(player) != null && pos2.get(player) != null) {
                            String name = "schematic";
                            if(args.length >= 2) {
                                name = args[1];
                            }
                            World world = player.getWorld();
                            LoadSchematics.saveSchematic(name, world, pos1.get(player), pos2.get(player));
                        } else {
                            bukkitPlayer.sendMessage("Bitte setze pos1 und pos2");
                        }
                        break;
                    default:
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}
