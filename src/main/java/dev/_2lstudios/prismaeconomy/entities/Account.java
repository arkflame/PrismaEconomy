package dev._2lstudios.prismaeconomy.entities;

import java.util.UUID;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class Account extends Entity {
    @ID
    public String id;

    @Prop
    public double balance = 0;

    @Prop
    public String uuid;

    @Prop
    public String name;

    public double getBalance() {
        return balance;
    }

    public void setBalance(final double balance) {
        this.balance = balance;
    }

    public UUID getUUID() {
        if (uuid == null) {
            return null;
        }
        
        return UUID.fromString(uuid);
    }

    public void setUUID(final UUID uuid) {
        this.uuid = uuid.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
