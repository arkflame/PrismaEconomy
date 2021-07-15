package dev._2lstudios.economy.account;

import java.util.UUID;

public class AccountProvider {
    public boolean exists(final UUID uuid) {
        return false;
    }

    public double getBalance(final UUID uuid) {
        // Overriden by superclass
        return 0;
    };

    public void remove(final UUID uuid) {
        // Overriden by superclass
    };

    public double setBalance(final UUID uuid, double amount) {
        // Overriden by superclass
        return 0;
    };

    public double addBalance(final UUID uuid, double amount) {
        final double newAmount = getBalance(uuid) + amount;

        setBalance(uuid, newAmount);

        return newAmount;
    }

    public boolean hasBalance(final UUID uuid, final double amount) {
        return getBalance(uuid) >= amount;
    }
}
