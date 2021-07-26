package dev._2lstudios.economy.providers.mongo;

import java.util.UUID;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
            return document.getInteger("balance");
        }

        return 0;
    }

    @Override
    public boolean remove(UUID uuid) {
        final String uuidString = uuid.toString();
        final int deletedCount = economyBalance.deleteMany(new Document("uuid", uuidString)).getDeletedCount();

        return deletedCount > 0;
    }

    @Override
    public double setBalance(UUID uuid, double amount) {
        final String uuidString = uuid.toString();
        
        return 0;
    }

    @Override
    public double addBalance(UUID uuid, double amount) {
        final String uuidString = uuid.toString();
        
        return 0;
    }

    @Override
    public boolean hasBalance(UUID uuid, double amount) {
        final String uuidString = uuid.toString();
        
        return false;
    }
}
