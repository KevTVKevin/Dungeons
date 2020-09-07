package de.kevtvkevin.dungeons.helper;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import de.kevtvkevin.dungeons.Dungeons;
import org.bukkit.Bukkit;
import com.sk89q.worldedit.world.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class LoadSchematics {

    public static void loadSchematic() {
        if(Dungeons.getInstance().getConfig().get("configPath") != null) {
            File dir = new File(Objects.requireNonNull(Dungeons.getInstance().getConfig().getString("configPath")));
            if(dir.list().length > 0) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    public static void saveSchematic(String name, World world, BlockVector3 min, BlockVector3 max) {
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
            File schematic = new File(Objects.requireNonNull(Dungeons.getInstance().getConfig().getString("configPath")), name + ".schem");
            if(schematic.isFile()) {
                try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(schematic))) {
                    writer.write(clipboard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}