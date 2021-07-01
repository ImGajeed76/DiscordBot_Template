package ch.imgajeed.discordBot.Bot.Vote;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@AllArgsConstructor
public class Vote implements Serializable {
    public String up = "⬆";
    public String down = "⬇";
    public String clear = "❎";

    public String title = "";
    public String messageID = "";

    public String upVoteName = "Up Votes";
    public String downVoteName = "Down Votes";

    public ArrayList<VotePerson> votes = new ArrayList<>();

    public boolean circleEnabled = false;

    public Vote(String title, boolean circleEnabled) {
        this.title = title;
        this.circleEnabled = circleEnabled;
    }

    public Vote(String title, boolean circleEnabled, String upVoteName, String downVoteName) {
        this.title = title;
        this.circleEnabled = circleEnabled;
        this.upVoteName = upVoteName;
        this.downVoteName = downVoteName;
    }

    public float getUpVotes() {
        var upVotes = 0;

        for (VotePerson person : votes) {
            if (person.upVote) {
                upVotes += 1;
            }
        }

        return upVotes;
    }

    public float getDownVotes() {
        var downVotes = 0;

        for (VotePerson person : votes) {
            if (!person.upVote) {
                downVotes += 1;
            }
        }

        return downVotes;
    }

    public String GetMessage() {
        int votes = Math.round(getUpVotes() / (getUpVotes() + getDownVotes()) * 40);

        if (getUpVotes() == 0 && getDownVotes() == 0) {
            votes = Math.round((getUpVotes() + 1) / ((getUpVotes() + 1) + (getDownVotes() + 1)) * 40);
        }

        var space = 40 - (upVoteName + ": " + Math.round(getUpVotes()) + downVoteName + ": " + Math.round(getDownVotes())).length();

        if (!circleEnabled) {
            return "> ** ```" + title + ": \n > \n > " +
                    "▓".repeat(Math.max(0, votes)) +
                    "░".repeat(Math.max(0, 40 - votes)) +
                    "\n > \n > " +
                    upVoteName + ": " + Math.round(getUpVotes()) + " ".repeat(Math.max(0, space)) + downVoteName + ": " + Math.round(getDownVotes()) + "``` **";
        } else {
            return "> ** ```" + title + ": \n > \n > " +
                    "-".repeat(Math.max(0, votes)) + "Ｏ" +
                    "-".repeat(Math.max(0, 40 - votes)) +
                    "\n > \n > " +
                    upVoteName + ": " + Math.round(getUpVotes()) + " ".repeat(Math.max(0, space + 1)) + downVoteName + ": " + Math.round(getDownVotes()) + "``` **";
        }
    }
}
