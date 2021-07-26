package dev._2lstudios.economy.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.economy.providers.AccountProvider;

public class EconomyCommand implements CommandExecutor {
    private final Plugin plugin;
    private final Server server;
    private final AccountProvider accountProvider;

    public EconomyCommand(Plugin plugin, Server server, AccountProvider accountProvider) {
        this.plugin = plugin;
        this.server = server;
        this.accountProvider = accountProvider;
    }

    private double toDouble(final String amountString) {
        try {
            return Double.parseDouble(amountString);
        } catch (NumberFormatException e) { }

        return 0;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        server.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (args.length > 0) {
                final String subcmd = args[0];

                if (args.length > 1) {
                    final String targetPlayer = args[1];

                    if (args.length > 2) {
                        final String amountString = args[2];

                        if (subcmd.equals("give")) {
                            final Player player = server.getPlayer(targetPlayer);
                            final double amount = toDouble(amountString);

                            accountProvider.addBalance(player.getUniqueId(), amount);
                        } else if (subcmd.equals("set")) {
                            final Player player = server.getPlayer(targetPlayer);
                            final double amount = toDouble(amountString);

                            accountProvider.setBalance(player.getUniqueId(), amount);
                        }
                    } else {
                        sender.sendMessage("no amount");
                    }
                } else {
                    sender.sendMessage("no player");
                }
            } else {
                sender.sendMessage("/eco <give/set> <player> <amount>");
            }
        });

        return true;
    }
}
