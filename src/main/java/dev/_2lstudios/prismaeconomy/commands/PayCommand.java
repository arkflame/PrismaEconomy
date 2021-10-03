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

public class PayCommand implements CommandExecutor {
    private final Plugin plugin;
    private final Server server;
    private final Economy economy;
    private final LangManager langManager;

    public PayCommand(final Plugin plugin, final Economy economy, final LangManager langManager) {
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
            if (sender instanceof Player) {
                final Player senderPlayer = (Player) sender;

                if (args.length > 0) {
                    final String targetPlayer = args[0];

                    if (args.length > 1) {
                        if (economy.hasAccount(targetPlayer)) {
                            final double amount = toDouble(args[1]);

                            if (economy.has(senderPlayer, amount)) {
                                economy.withdrawPlayer(senderPlayer, amount);
                                economy.depositPlayer(targetPlayer, amount);
                                langManager.sendMessage(sender, "commands.pay.sender",
                                        new Placeholder("%sender%", sender.getName()),
                                        new Placeholder("%receiver%", targetPlayer),
                                        new Placeholder("%amount%", amount));
                                langManager.sendMessage(server.getPlayer(targetPlayer), "commands.pay.receiver",
                                        new Placeholder("%sender%", sender.getName()),
                                        new Placeholder("%receiver%", targetPlayer),
                                        new Placeholder("%amount%", amount));
                            } else {
                                langManager.sendMessage(sender, "error.no_balance");
                            }
                        } else {
                            langManager.sendMessage(sender, "error.no_account");
                        }
                    } else {
                        langManager.sendMessage(sender, "error.no_amount");
                    }
                } else {
                    langManager.sendMessage(sender, "error.no_player");
                }
            } else {
                langManager.sendMessage(sender, "error.console");
            }
        });

        return true;
    }
}
