package dev._2lstudios.economy.account.yaml;

import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import dev._2lstudios.economy.account.AccountProvider;
import dev._2lstudios.economy.utils.ConfigurationUtil;

public class YamlAccountProvider extends AccountProvider {
    private final ConfigurationUtil configurationUtil;

    public YamlAccountProvider(final ConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;
    }

    @Override
    public boolean exists(final UUID uuid) {
        return configurationUtil.exists("%datafolder%/data/" + uuid.toString());
    }

    @Override
    public void remove(UUID uuid) {
        configurationUtil.deleteConfiguration("%datafolder%/data/" + uuid.toString());
    }

    @Override
    public double getBalance(final UUID uuid) {
        final YamlConfiguration config = configurationUtil.getConfiguration("%datafolder%/data/" + uuid.toString());

        return config.getDouble("balance");
    }

    @Override
    public double setBalance(final UUID uuid, double amount) {
        final YamlConfiguration config = configurationUtil.getConfiguration("%datafolder%/data/" + uuid.toString());

        config.set("balance", amount);
        configurationUtil.saveConfiguration(config, "%datafolder%/data/" + uuid.toString(), false);

        return amount;
    }
}
