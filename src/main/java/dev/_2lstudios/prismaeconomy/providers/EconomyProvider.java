package dev._2lstudios.prismaeconomy.providers;

import java.util.List;
import java.util.UUID;

import com.avaje.ebean.validation.NotNull;
import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.providers.Provider;
import com.dotphin.milkshakeorm.repository.Repository;
import com.dotphin.milkshakeorm.utils.MapFactory;
import com.mongodb.lang.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.prismaeconomy.entities.Account;
import dev._2lstudios.prismaeconomy.utils.MongoURIBuilder;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EconomyProvider implements Economy {
    private final Plugin plugin;
    private final Repository<Account> accountRepository;

    public EconomyProvider(final Plugin plugin, final MongoURIBuilder mongoURIBuilder) {
        final Provider provider = MilkshakeORM.connect(mongoURIBuilder.toURI().getConnectionString());
        MilkshakeORM.addRepository(Account.class, provider, "accounts");
        this.plugin = plugin;
        this.accountRepository = MilkshakeORM.getRepository(Account.class);
    }

    @NotNull
    private Account findByName(final String name) {
        final Account found = accountRepository.findOne(MapFactory.create("name", name));

        if (found != null) {
            return found;
        }

        final Account account = new Account();

        account.setName(name);

        return account;
    }

    @Nullable
    private Account findByUUID(final UUID uuid) {
        return accountRepository.findOne(MapFactory.create("uuid", uuid.toString()));
    }

    @NotNull
    private Account findByPlayer(final OfflinePlayer offlinePlayer) {
        final Account accountByUUID = findByUUID(offlinePlayer.getUniqueId());

        if (accountByUUID != null) {
            accountByUUID.setName(offlinePlayer.getName());

            return accountByUUID;
        }

        final Account accountByName = findByName(offlinePlayer.getName());

        accountByName.setUUID(offlinePlayer.getUniqueId());

        return accountByName;
    }

    private EconomyResponse deposit(final Account account, final double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        } else if (account != null) {
            account.setBalance(account.getBalance() + amount);
            account.save();

            return new EconomyResponse(amount, account.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Ok");
        } else {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Account does not exist");
        }
    }

    private EconomyResponse withdraw(final Account account, final double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        } else if (account != null) {
            account.setBalance(account.getBalance() - amount);
            account.save();

            return new EconomyResponse(amount, account.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Ok");
        } else {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Account does not exist");
        }
    }

    private boolean has(final Account account, final double amount) {
        if (account != null) {
            return account.getBalance() >= amount;
        }

        return false;
    }

    @Override
    public EconomyResponse bankBalance(String arg0) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String arg0, double arg1) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String arg0, double arg1) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String arg0, double arg1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String arg0, String arg1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String arg0) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer arg0) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String arg0, String arg1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {
        return false;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String arg0) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String arg0, double arg1) {
        return deposit(findByName(arg0), arg1);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer arg0, double arg1) {
        return deposit(findByPlayer(arg0), arg1);
    }

    @Override
    public EconomyResponse depositPlayer(String arg0, String arg1, double arg2) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer arg0, String arg1, double arg2) {
        return null;
    }

    @Override
    public String format(double arg0) {
        return null;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public double getBalance(String arg0) {
        return findByName(arg0).getBalance();
    }

    @Override
    public double getBalance(OfflinePlayer arg0) {
        return findByPlayer(arg0).getBalance();
    }

    @Override
    public double getBalance(String arg0, String arg1) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer arg0, String arg1) {
        return 0;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public String getName() {
        return plugin.getName();
    }

    @Override
    public boolean has(String arg0, double arg1) {
        return has(findByName(arg0), arg1);
    }

    @Override
    public boolean has(OfflinePlayer arg0, double arg1) {
        return has(findByPlayer(arg0), arg1);
    }

    @Override
    public boolean has(String arg0, String arg1, double arg2) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer arg0, String arg1, double arg2) {
        return false;
    }

    @Override
    public boolean hasAccount(String arg0) {
        return accountRepository.findOne(MapFactory.create("name", arg0)) != null;
    }

    @Override
    public boolean hasAccount(OfflinePlayer arg0) {
        return accountRepository.findOne(MapFactory.create("uuid", arg0.getUniqueId())) != null;
    }

    @Override
    public boolean hasAccount(String arg0, String arg1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer arg0, String arg1) {
        return false;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public EconomyResponse isBankMember(String arg0, String arg1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String arg0, String arg1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return plugin.isEnabled();
    }

    @Override
    public EconomyResponse withdrawPlayer(String arg0, double arg1) {
        return withdraw(findByName(arg0), arg1);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer arg0, double arg1) {
        return withdraw(findByPlayer(arg0), arg1);
    }

    @Override
    public EconomyResponse withdrawPlayer(String arg0, String arg1, double arg2) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer arg0, String arg1, double arg2) {
        return null;
    }
}
