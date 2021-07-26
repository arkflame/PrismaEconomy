package dev._2lstudios.economy.providers.mongo;

import java.util.UUID;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import org.bson.Document;

import dev._2lstudios.economy.providers.EconomyProvider;
import dev._2lstudios.economy.utils.MongoURIBuilder;

public class MongoEconomyProvider implements EconomyProvider {
    private final MongoCollection<Document> economyBalance;

    public MongoEconomyProvider(final MongoURIBuilder mongoURIBuilder) {
        final MongoClient client = MongoClients.create(mongoURIBuilder.toURI());
        final MongoDatabase mongoDatabase = client.getDatabase(mongoURIBuilder.getDatabase());

        this.economyBalance = mongoDatabase.getCollection("economy_balance");
    }

    @Override
    public boolean exists(UUID uuid) {
        final String uuidString = uuid.toString();
        final Document document = economyBalance.find(new Document("uuid", uuidString)).first();

        return document != null;
    }

    @Override
    public double getBalance(UUID uuid) {
        final String uuidString = uuid.toString();
        final Document document = economyBalance.find(new Document("uuid", uuidString)).first();

        if (document != null) {
            return document.getDouble("balance");
        }

        return 0;
    }

    @Override
    public boolean delete(UUID uuid) {
        final String uuidString = uuid.toString();
        final long deletedCount = economyBalance.deleteMany(new Document("uuid", uuidString)).getDeletedCount();

        return deletedCount > 0;
    }

    @Override
    public double setBalance(UUID uuid, double amount) {
        final String uuidString = uuid.toString();

        economyBalance.updateOne(new Document("uuid", uuidString),
                new Document("$set", new Document("balance", amount)), new UpdateOptions().upsert(true));

        return amount;
    }

    @Override
    public double addBalance(UUID uuid, double amount) {
        final Document uuidFilter = new Document("uuid", uuid.toString());
        final Document document = economyBalance.find(uuidFilter).first();

        if (document != null) {
            final double newAmount = document.getDouble("balance") + amount;

            economyBalance.updateOne(uuidFilter, new Document("$set", new Document("balance", newAmount)),
                    new UpdateOptions().upsert(true));

            return newAmount;
        }

        return 0;
    }

    @Override
    public boolean hasBalance(UUID uuid, double amount) {
        final String uuidString = uuid.toString();
        final Document document = economyBalance.find(new Document("uuid", uuidString)).first();

        if (document != null) {
            final double userAmount = document.getDouble("balance");

            return userAmount >= amount;
        }

        return false;
    }
}
