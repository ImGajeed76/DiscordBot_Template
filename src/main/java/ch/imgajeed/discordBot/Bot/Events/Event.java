package ch.imgajeed.discordBot.Bot.Events;

import java.util.ArrayList;

public class Event {
    public String name;
    public String time;

    public String messageID;
    public ArrayList<String> peopleID = new ArrayList<>();
    public ArrayList<String> peopleName = new ArrayList<>();

    public Event(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String GetMessage() {
        StringBuilder people = new StringBuilder();

        for (String person : this.peopleID) {
            people.append("> ").append(peopleName).append(" \n");
        }

        return "> ** ```" + name + ": " + time +
                " \n > \n > Participants: \n" +
                people + "> ``` **";
    }
}
