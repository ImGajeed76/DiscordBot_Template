package ch.imgajeed.discordBot.Bot;

import ch.imgajeed.discordBot.Bot.Events.Event;
import ch.imgajeed.discordBot.Bot.RandomTeams.Team;
import ch.imgajeed.discordBot.Bot.Vote.Vote;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Parameters implements Serializable {
    public String date;

    public ArrayList<ReactionAction> reactionActions = new ArrayList<>();
    public ArrayList<Vote> votes = new ArrayList<>();
    public ArrayList<Event> events = new ArrayList<>();
    public ArrayList<Team> teams = new ArrayList<>();

    public Parameters(ArrayList<ReactionAction> reactionActions, ArrayList<Vote> votes, ArrayList<Event> events, ArrayList<Team> teams) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.date = dtf.format(now);

        this.reactionActions = reactionActions;
        this.votes = votes;
        this.events = events;
        this.teams = teams;
    }
}
