package ch.imgajeed.discordBot.Bot;

import ch.imgajeed.discordBot.Bot.Events.Event;
import ch.imgajeed.discordBot.Bot.Vote.Vote;

import java.util.ArrayList;

public class Parameters {
    public ArrayList<ReactionAction> reactionActions;
    public ArrayList<Vote> votes;
    public ArrayList<Event> events;

    public Parameters(ArrayList<ReactionAction> reactionActions, ArrayList<Vote> votes, ArrayList<Event> events) {
        this.reactionActions = reactionActions;
        this.votes = votes;
        this.events = events;
    }
}
