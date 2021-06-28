package ch.imgajeed.discordBot.Bot.Vote;

import net.dv8tion.jda.api.entities.User;

public class VotePerson {
    public boolean upVote;
    public String userID;

    public VotePerson(boolean upVote, String userID) {
        this.upVote = upVote;
        this.userID = userID;
    }
}
