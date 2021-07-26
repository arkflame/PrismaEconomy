package dev._2lstudios.economy;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.economy.commands.BalanceCommand;
import dev._2lstudios.economy.commands.EconomyCommand;
import dev._2lstudios.economy.providers.AccountProvider;
import dev._2lstudios.economy.providers.mongo.MongoAccountProvider;
import dev._2lstudios.economy.utils.ConfigurationUtil;
import dev._2lstudios.economy.utils.MongoURIBuilder;

public class Economy extends JavaPlugin {
    @Override
    public void onEnable() {
        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        final Configuration config = configurationUtil.getConfiguration("%datafolder%/config.yml");
        final AccountProvider accountProvider = new MongoAccountProvider(new MongoURIBuilder().build(config));

        getCommand("balance").setExecutor(new BalanceCommand(this, getServer(), accountProvider));
        getCommand("economy").setExecutor(new EconomyCommand(this, getServer(), accountProvider));
    }
}