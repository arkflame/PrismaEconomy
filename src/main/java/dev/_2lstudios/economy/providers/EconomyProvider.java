package dev._2lstudios.economy.providers;

import java.util.UUID;

public interface EconomyProvider {
    public boolean exists(final UUID uuid);

    public double getBalance(final UUID uuid);

    public boolean delete(final UUID uuid);

    public double setBalance(final UUID uuid, final double amount);

    public double addBalance(final UUID uuid, final double amount);

    public boolean hasBalance(final UUID uuid, final double amount);
}
