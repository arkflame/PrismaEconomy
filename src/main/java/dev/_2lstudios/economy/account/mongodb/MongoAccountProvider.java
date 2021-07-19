package dev._2lstudios.economy.account.mongodb;

import java.util.UUID;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import dev._2lstudios.economy.account.AccountProvider;

public class MongoAccountProvider extends AccountProvider {
    private final MongoCollection<Document> collection;

    public MongoAccountProvider(final String uri, final String database, final String collection) {
        final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(uri))
            .retryWrites(true)
            .build();
        final MongoClient client = MongoClients.create(settings);
        final MongoDatabase mongoDatabase = client.getDatabase(database);

        this.collection = mongoDatabase.getCollection(collection);
        this.collection.countDocuments();
    }

    private Document toMongoDocument(final UUID uuid, final double amount) {
        return new Document().append("uuid", uuid.toString()).append("balance", amount);
    }

    private Document toFilter(final UUID uuid) {
        return new Document("uuid", uuid.toString());
    }

    private Document getDocument(final UUID uuid) {
        return collection.find(toFilter(uuid)).first();
    }

    @Override
    public boolean exists(UUID uuid) {
        return getDocument(uuid) != null;
    }

    @Override
    public void remove(UUID uuid) {
        collection.deleteOne(toFilter(uuid));
    }

    @Override
    public double getBalance(final UUID uuid) {
        Document result = collection.find(toFilter(uuid)).first();

        if (result != null && result.containsKey("balance")) {
            return result.getDouble("balance");
        } else {
            return 0;
        }
    }

    @Override
    public double setBalance(final UUID uuid, double amount) {
        final Document document = toMongoDocument(uuid, amount);
        final Document result = this.collection.find(new Document("uuid", uuid.toString())).first();

        if (result == null) {
            this.collection.insertOne(document);
        } else {
            this.collection.updateOne(result, document);
        }

        return amount;
    }
}
