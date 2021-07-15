package dev._2lstudios.economy.account;

import java.util.UUID;

public class AccountProvider {
    public double get(final UUID uuid) {
        // Overriden by superclass
        return 0;
    };

    public void remove(final UUID uuid) {
        // Overriden by superclass
    };

    public double set(final UUID uuid, double amount) {
        // Overriden by superclass
        return 0;
    };

    public double add(final UUID uuid, double amount) {
        final double newAmount = get(uuid) + amount;

        set(uuid, newAmount);

        return newAmount;
    }

    public boolean has(final UUID uuid, final double amount) {
        return get(uuid) >= amount;
    }
}
