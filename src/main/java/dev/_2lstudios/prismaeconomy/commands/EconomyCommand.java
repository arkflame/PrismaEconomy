package dev._2lstudios.prismaeconomy.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.prismaeconomy.lang.LangManager;
import dev._2lstudios.prismaeconomy.placeholders.Placeholder;
import net.milkbowl.vault.economy.Economy;

public class EconomyCommand implements CommandExecutor {
    private final Plugin plugin;
    private final Server server;
    private final Economy economy;
    private final LangManager langManager;

    public EconomyCommand(final Plugin plugin, final Economy economy, final LangManager langManager) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.economy = economy;
        this.langManager = langManager;
    }

    private double toDouble(final String amountString) {
        try {
            return Double.parseDouble(amountString);
        } catch (final NumberFormatException e) {
        }

        return 0;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        server.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (args.length > 0) {
                final String subcmd = args[0];

                if (args.length > 1) {
                    final String targetPlayer = args[1];

                    if (args.length > 2) {
                        final String amountString = args[2];
                        final Player player = server.getPlayer(targetPlayer);
                        final double amount = toDouble(amountString);

                        if (subcmd.equals("give")) {
                            if (amount >= 0) {
                                economy.depositPlayer(targetPlayer, amount);
                                langManager.sendMessage(sender, "commands.economy.give.receiver",
                                        new Placeholder("%sender%", sender.getName()),
                                        new Placeholder("%receiver%", player.getName()),
                                        new Placeholder("%amount%", amount));
                                langManager.sendMessage(player, "commands.economy.give.sender",
                                        new Placeholder("%sender%", sender.getName()),
                                        new Placeholder("%receiver%", player.getName()),
                                        new Placeholder("%amount%", amount));
                            } else {
                                langManager.sendMessage(sender, "error.negative");
                            }
                        } else if (subcmd.equals("take")) {
                            if (amount >= 0) {
                                economy.withdrawPlayer(targetPlayer, amount);
                                langManager.sendMessage(sender, "commands.economy.take.receiver",
                                        new Placeholder("%sender%", sender.getName()),
                                        new Placeholder("%receiver%", player.getName()),
                                        new Placeholder("%amount%", amount));
                                langManager.sendMessage(player, "commands.economy.take.sender",
                                        new Placeholder("%sender%", sender.getName()),
                                        new Placeholder("%receiver%", player.getName()),
                                        new Placeholder("%amount%", amount));
                            } else {
                                langManager.sendMessage(sender, "error.negative");
                            }
                        } else if (subcmd.equals("set")) {
                            economy.withdrawPlayer(targetPlayer, economy.getBalance(player));
                            economy.depositPlayer(targetPlayer, amount);
                            langManager.sendMessage(sender, "commands.economy.set.receiver",
                                    new Placeholder("%sender%", sender.getName()),
                                    new Placeholder("%receiver%", player.getName()),
                                    new Placeholder("%amount%", amount));
                            langManager.sendMessage(player, "commands.economy.set.sender",
                                    new Placeholder("%sender%", sender.getName()),
                                    new Placeholder("%receiver%", player.getName()),
                                    new Placeholder("%amount%", amount));
                        }
                    } else {
                        langManager.sendMessage(sender, "error.no_amount");
                    }
                } else {
                    langManager.sendMessage(sender, "error.no_player");
                }
            } else {
                langManager.sendMessage(sender, "commands.economy.help", new Placeholder("%label%", label));
            }
        });

        return true;
    }
}
