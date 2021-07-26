package dev._2lstudios.economy.placeholders;

public class Placeholder {
    private final String key;
    private final String value;

    public Placeholder(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String replace(final String string) {
        return string.replace(key, value);
    }
}
