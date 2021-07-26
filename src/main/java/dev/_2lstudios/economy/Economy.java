package dev._2lstudios.economy;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.economy.commands.BalanceCommand;
import dev._2lstudios.economy.commands.EconomyCommand;
import dev._2lstudios.economy.lang.LangManager;
import dev._2lstudios.economy.providers.EconomyProvider;
import dev._2lstudios.economy.providers.mongo.MongoEconomyProvider;
import dev._2lstudios.economy.utils.ConfigUtil;
import dev._2lstudios.economy.utils.MongoURIBuilder;

public class Economy extends JavaPlugin {
    @Override
    public void onEnable() {
        final ConfigUtil configUtil = new ConfigUtil(this);

        configUtil.create("%datafolder%/config.yml", "config.yml");

        final Configuration config = configUtil.get("%datafolder%/config.yml");
        final LangManager langManager = new LangManager(configUtil, config.getString("lang"));
        final EconomyProvider accountProvider = new MongoEconomyProvider(new MongoURIBuilder().build(config));

        getCommand("balance").setExecutor(new BalanceCommand(this, accountProvider));
        getCommand("economy").setExecutor(new EconomyCommand(this, accountProvider));
    }
}