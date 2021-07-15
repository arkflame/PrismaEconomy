package dev._2lstudios.economy.utils;

import org.bukkit.configuration.Configuration;

public class URIUtils {
    public static String getURIFromConfig(final Configuration config) {
        return getURIFromConfig(config.getString("provider.driver").toLowerCase(), config.getString("provider.host"),
                config.getInt("provider.port"), config.getString("provider.user"), config.getString("provider.pass"),
                config.getString("provider.database"));
    }

    public static String getURIFromConfig(final String protocol, final String host, final int port,
            final String username, final String password, final String path) {
        String uri = protocol + "://";

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            uri += username + ":" + password + "@" + host;
        } else {
            uri += host;
        }

        if (port != Integer.MIN_VALUE) {
            uri += ":" + port;
        }

        if (path != null & !path.isEmpty()) {
            uri += "/" + path;
        }

        return uri;
    }
}