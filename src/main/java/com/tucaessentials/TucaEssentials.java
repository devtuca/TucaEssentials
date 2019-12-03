package com.tucaessentials;

import commands.Clear;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class TucaEssentials extends JavaPlugin {

    public static File filePlayerHomes = new File("plugins/Essentials/Homes/homes.yml");
    public static YamlConfiguration homes = YamlConfiguration.loadConfiguration(filePlayerHomes);

    @Override
    public void onEnable() {
        registerCommands();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void criarConfig() {
        if (!filePlayerHomes.exists()) {
            saveHomesFile();
        }
    }

    public void registerCommands() {
        getCommand("clear").setExecutor(new Clear());
    }

    public static void saveHomesFile() {
        try {
            homes.save(filePlayerHomes);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static TucaEssentials getPlugin() {
        return getPlugin(TucaEssentials.class);
    }
 }
