package ch.imgajeed.discordBot.Bot;

import java.io.Serializable;

public class Person implements Serializable {
    public String userID;
    public String name;

    public Person(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }
}
