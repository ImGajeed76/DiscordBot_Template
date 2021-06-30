package ch.imgajeed.discordBot.Bot.Events;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@AllArgsConstructor
public class Event implements Serializable {
    public String add = "✅";

    public String name = "";
    public String time = "";

    public String messageID = "";
    public ArrayList<String> peopleID = new ArrayList<>();
    public ArrayList<String> peopleName = new ArrayList<>();
    public String remove = "❎";

    public Event(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String GetMessage() {
        StringBuilder people = new StringBuilder();

        for (String person : this.peopleName) {
            people.append("> ").append(person).append(" \n");
        }

        return "> ** ```" + name + ": " + time +
                " \n > \n > Participants: \n" +
                people + "> ``` **";
    }
}
