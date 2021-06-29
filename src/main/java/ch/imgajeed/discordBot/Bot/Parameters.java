package ch.imgajeed.discordBot.Bot;

import ch.imgajeed.discordBot.Bot.Events.Event;
import ch.imgajeed.discordBot.Bot.RandomTeams.Team;
import ch.imgajeed.discordBot.Bot.Vote.Vote;

import java.util.ArrayList;

public class Parameters {
    public ArrayList<ReactionAction> reactionActions;
    public ArrayList<Vote> votes;
    public ArrayList<Event> events;
    public ArrayList<Team> teams;

    public Parameters(ArrayList<ReactionAction> reactionActions, ArrayList<Vote> votes, ArrayList<Event> events, ArrayList<Team> teams) {
        this.reactionActions = reactionActions;
        this.votes = votes;
        this.events = events;
        this.teams = teams;
    }
}
