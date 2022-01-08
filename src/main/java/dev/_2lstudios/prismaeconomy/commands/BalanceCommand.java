package dev._2lstudios.prismaeconomy.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.prismaeconomy.lang.LangManager;
import dev._2lstudios.prismaeconomy.placeholders.Placeholder;
import net.milkbowl.vault.economy.Economy;

public class BalanceCommand implements CommandExecutor {
    private final Plugin plugin;
    private final Server server;
    private final LangManager langManager;
    private final Economy economy;

    public BalanceCommand(final Plugin plugin, final LangManager langManager, final Economy economy) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.langManager = langManager;
        this.economy = economy;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        server.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (args.length > 0) {
                String playerName = args[0];

                langManager.sendMessage(sender, "commands.balance.other",
                        new Placeholder("%balance%", economy.getBalance(playerName)),
                        new Placeholder("%other%", playerName));
            } else if (sender instanceof OfflinePlayer) {
                langManager.sendMessage(sender, "commands.balance.you",
                        new Placeholder("%balance%", economy.getBalance((OfflinePlayer) sender)));
            } else {
                langManager.sendMessage(sender, "error.console");
            }
        });

        return true;
    }
}
