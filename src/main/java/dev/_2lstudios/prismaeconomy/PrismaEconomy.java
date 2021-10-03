package dev._2lstudios.prismaeconomy;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.prismaeconomy.commands.BalanceCommand;
import dev._2lstudios.prismaeconomy.commands.EconomyCommand;
import dev._2lstudios.prismaeconomy.commands.PayCommand;
import dev._2lstudios.prismaeconomy.lang.LangManager;
import dev._2lstudios.prismaeconomy.providers.EconomyProvider;
import dev._2lstudios.prismaeconomy.utils.ConfigUtil;
import dev._2lstudios.prismaeconomy.utils.MongoURIBuilder;
import net.milkbowl.vault.economy.Economy;

public class PrismaEconomy extends JavaPlugin {
  @Override
  public void onEnable() {
    final ConfigUtil configUtil = new ConfigUtil(this);

    configUtil.create("%datafolder%/config.yml", "config.yml");

    final Configuration config = configUtil.get("%datafolder%/config.yml");
    final LangManager langManager = new LangManager(configUtil, config.getString("lang"));
    final EconomyProvider economyProvider = new EconomyProvider(this, new MongoURIBuilder().build(config));

    getServer().getServicesManager().register(Economy.class, economyProvider, this, ServicePriority.Normal);

    getCommand("balance").setExecutor(new BalanceCommand(this, langManager, economyProvider));
    getCommand("prismaeconomy").setExecutor(new EconomyCommand(this, economyProvider, langManager));
    getCommand("pay").setExecutor(new PayCommand(this, economyProvider, langManager));
  }
}