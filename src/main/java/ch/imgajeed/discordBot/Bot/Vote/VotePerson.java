package ch.imgajeed.discordBot.Bot.Vote;

import java.io.Serializable;

public class VotePerson implements Serializable {
    public boolean upVote = false;
    public String userID = "";

    public VotePerson(boolean upVote, String userID) {
        this.upVote = upVote;
        this.userID = userID;
    }
}
