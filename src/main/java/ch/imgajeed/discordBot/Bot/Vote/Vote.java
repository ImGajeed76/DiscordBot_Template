package ch.imgajeed.discordBot.Bot.Vote;

public class Vote {
    public String title;
    public String messageID = "";
    public float upVotes = 0f;
    public float downVotes = 0f;

    public boolean circleEnabled;

    public Vote(String title, boolean circleEnabled) {
        this.title = title;
        this.circleEnabled = circleEnabled;
    }

    public String GetMessage() {
        int votes = Math.round(upVotes / (upVotes + downVotes) * 40);

        if (upVotes == 0 && downVotes == 0) {
            votes = Math.round((upVotes + 1) / ((upVotes + 1) + (downVotes + 1)) * 40);
        }

        var space = 40 - ("Up Votes: " + Math.round(upVotes) + "Down Votes: " + Math.round(downVotes)).length();

        if (!circleEnabled) {
            return "> ** ```" + title + ": \n > \n > " +
                    "▓".repeat(Math.max(0, votes)) +
                    "░".repeat(Math.max(0, 40 - votes)) +
                    "\n > \n > " +
                    "Up Votes: " + Math.round(upVotes) + " ".repeat(Math.max(0, space)) + "Down Votes: " + Math.round(downVotes) + "``` **";
        } else {
            return "> ** ```" + title + ": \n > \n > " +
                    "-".repeat(Math.max(0, votes)) + "Ｏ" +
                    "-".repeat(Math.max(0, 40 - votes)) +
                    "\n > \n > " +
                    "Up Votes: " + Math.round(upVotes) + " ".repeat(Math.max(0, space + 1)) + "Down Votes: " + Math.round(downVotes) + "``` **";
        }
    }
}
