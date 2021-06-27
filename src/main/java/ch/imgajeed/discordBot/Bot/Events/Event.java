package ch.imgajeed.discordBot.Bot.Events;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

public class Event {
    public String name;
    public String time;

    public String messageID;
    public ArrayList<User> people = new ArrayList<>();

    public Event(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String GetMessage() {
        StringBuilder people = new StringBuilder();

        for (User person : this.people) {
            people.append("> ").append(person.getName()).append(" \n");
        }

        return "> ** ```" + name + ": " + time +
                " \n > \n > Participants: \n" +
                people + "> ``` **";
    }
}
