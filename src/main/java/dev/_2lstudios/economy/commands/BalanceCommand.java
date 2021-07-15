package dev._2lstudios.economy.commands;

import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.economy.account.AccountProvider;

public class BalanceCommand implements CommandExecutor {
    private final Plugin plugin;
    private final Server server;
    private final AccountProvider accountProvider;

    public BalanceCommand(Plugin plugin, Server server, AccountProvider accountProvider) {
        this.plugin = plugin;
        this.server = server;
        this.accountProvider = accountProvider;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        server.getScheduler().runTaskAsynchronously(plugin, () -> {
            final String playerName;

            if (args.length > 0) {
                playerName = args[0];
            } else {
                playerName = sender.getName();
            }

            final Player player = server.getPlayer(playerName);

            if (player != null) {
                final UUID uuid = player.getUniqueId();
                final double balance = accountProvider.get(uuid);

                sender.sendMessage("Balance: " + balance);
            }
        });

        return true;
    }
}
