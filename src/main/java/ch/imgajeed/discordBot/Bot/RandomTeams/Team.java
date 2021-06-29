package ch.imgajeed.discordBot.Bot.RandomTeams;

import ch.imgajeed.discordBot.Bot.Person;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

@AllArgsConstructor
public class Team implements Serializable {
    public String add = "✅";
    public String generate = "\uD83C\uDD97";

    public ArrayList<ArrayList<Person>> teams = new ArrayList<>();
    public ArrayList<Integer> usedPersons = new ArrayList();

    public ArrayList<Person> persons = new ArrayList<>();
    public int teamSize = 1;
    public String title = "";
    public String messageID = "";

    public Team(String title, int teamSize) {
        this.title = title;
        this.teamSize = teamSize;
    }

    public String GetMessage() {
        StringBuilder people = new StringBuilder();
        for (Person person : persons) {
            people.append("> ").append(person.name).append(" \n");
        }

        return "> ** ```" + title + ":" +
                " \n > \n > Participants: \n" +
                people + "> ``` **";
    }

    public String GetTeams() {
        CreateTeams();
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < teams.size(); i++) {
            message.append("> Team ").append(i + 1).append(":").append("\n");

            for (Person person : teams.get(i)) {
                message.append(">   ").append(person.name).append("\n");
            }

            message.append("> \n");
        }

        return "> ** ```Teams: \n > \n" + message + "> ``` **";
    }

    public void CreateTeams() {
        int tolerance = persons.size() % teamSize;
        int teamCount = (persons.size() - tolerance) / teamSize;

        for (int i = 0; i < teamCount; i++) {
            ArrayList<Person> team = new ArrayList<>();

            for (int j = 0; j < teamSize; j++) {
                team.add(RandomPerson());
            }

            teams.add(team);
        }

        if (tolerance != 0) {
            ArrayList<Person> team = new ArrayList<>();

            for (int i = 0; i < tolerance; i++) {
                team.add(RandomPerson());
            }

            teams.add(team);
        }
    }

    private Person RandomPerson() {
        Random random = new Random();
        int number = random.nextInt(persons.size());

        if (!intInArray(usedPersons, number)) {
            usedPersons.add(number);
            return persons.get(number);
        } else {
            return RandomPerson();
        }
    }

    private boolean intInArray(ArrayList<Integer> array, int number) {
        for (Integer n : array) {
            if (n == number) {
                return true;
            }
        }

        return false;
    }
}
