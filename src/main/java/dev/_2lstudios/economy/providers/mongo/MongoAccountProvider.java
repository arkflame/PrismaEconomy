package dev._2lstudios.economy.providers.mongo;

import java.util.UUID;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import dev._2lstudios.economy.providers.AccountProvider;
import dev._2lstudios.economy.utils.MongoURIBuilder;

public class MongoAccountProvider implements AccountProvider {
    private final MongoCollection<Document> economyBalance;

    public MongoAccountProvider(final MongoURIBuilder mongoURIBuilder) {
        final MongoClient client = MongoClients.create(mongoURIBuilder.toURI());
        final MongoDatabase mongoDatabase = client.getDatabase(mongoURIBuilder.getDatabase());

        this.economyBalance = mongoDatabase.getCollection("economy_balance");
    }

    @Override
    public boolean exists(UUID uuid) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getBalance(UUID uuid) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void remove(UUID uuid) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double setBalance(UUID uuid, double amount) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double addBalance(UUID uuid, double amount) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasBalance(UUID uuid, double amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
