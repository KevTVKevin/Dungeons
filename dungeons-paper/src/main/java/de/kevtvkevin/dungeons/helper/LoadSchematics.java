package de.kevtvkevin.dungeons.helper;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.kevtvkevin.dungeons.Dungeons;
import org.bukkit.Bukkit;
import com.sk89q.worldedit.world.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class LoadSchematics {

    public static File exists(String name) {
        if(Dungeons.getInstance().getConfig().get("configPath") != null) {
            File file = new File(Objects.requireNonNull(Dungeons.getInstance().getConfig().getString("configPath")), name + ".schem");
            if(file.exists()) {
                return file;
            }
        }
        return null;
    }

    public static void loadSchematic(File schem, Player player) {
        ClipboardFormat format = ClipboardFormats.findByFile(schem);
        Clipboard clipboard = null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schem))) {
            try {
                clipboard = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(clipboard != null) {
            World world = player.getWorld();
            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(player.getLocation().toVector().toBlockPoint())
                        .ignoreAirBlocks(false)
                        .build();
                Operations.complete(operation);
            } catch (WorldEditException e) {
                e.printStackTrace();
            }
        }

    }

    public static void saveSchematic(String name, World world, BlockVector3 min, BlockVector3 max) {
        System.out.println("started");
        CuboidRegion region = new CuboidRegion(world, min, max);
        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(region.getWorld(), -1);

        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
        forwardExtentCopy.setCopyingEntities(true);
        try {
            Operations.complete(forwardExtentCopy);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
        if(Dungeons.getInstance().getConfig().get("configPath") != null) {
            System.out.println("config");
            File schematic = new File(Objects.requireNonNull(Dungeons.getInstance().getConfig().getString("configPath")), name + ".schem");
            try {
                schematic.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(schematic.isFile()) {
                System.out.println("file");
                try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(schematic))) {
                    writer.write(clipboard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No config");
        }
    }
}