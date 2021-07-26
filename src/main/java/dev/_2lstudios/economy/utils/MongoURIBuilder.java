package dev._2lstudios.economy.utils;

import com.mongodb.ConnectionString;

import org.bukkit.configuration.Configuration;

public class MongoURIBuilder {
    private String host;
    private int port;
    private String user;
    private String pass;
    private String database;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public MongoURIBuilder build(final Configuration config) {
        setHost(config.getString("provider.host"));
        setPort(config.getInt("provider.port"));
        setUser(config.getString("provider.user"));
        setPass(config.getString("provider.pass"));
        setDatabase(config.getString("provider.database"));

        return this;
    }

    public ConnectionString toURI() {
        String uri = "mongodb://";

        if (user != null && !user.isEmpty() && pass != null && !pass.isEmpty()) {
            uri += pass + ":" + pass + "@" + host;
        } else {
            uri += host;
        }

        if (port != Integer.MIN_VALUE) {
            uri += ":" + port;
        }

        if (database != null & !database.isEmpty()) {
            uri += "/" + database;
        }

        return new ConnectionString(uri);
    }
}