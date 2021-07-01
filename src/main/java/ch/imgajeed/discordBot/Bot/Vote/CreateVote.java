package ch.imgajeed.discordBot.Bot.Vote;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.MessageAction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CreateVote extends MessageAction {
    @Override
    public int contentLength() {
        return 2;
    }

    @Override
    public String name() {
        return "CreateVote";
    }

    @Override
    public String content() {
        return "*:Title* :*CircleDesignEnabled(true / false)* (:*UpVoteName* :*DownVoteName*)";
    }

    @Override
    public void Run(@NotNull MessageReceivedEvent event, Listener listener) {
        var content = listener.GetContent(event.getMessage().getContentRaw());
        if (content.size() < contentLength()) {
            listener.ContentToShort(event.getChannel());
            return;
        } else if (content.size() > contentLength() && content.size() < contentLength() + 2) {
            listener.ContentToShort(event.getChannel());
            return;
        }

        var title = content.get(0);
        var circleEnabled = content.get(1).equals("true");
        Vote vote = new Vote(title, circleEnabled);

        if (content.size() > contentLength()) {
            vote = new Vote(title, circleEnabled, content.get(2), content.get(3));
        }

        Vote finalVote = vote;
        event.getChannel().sendMessage(vote.GetMessage()).queue(message -> {
            finalVote.messageID = message.getId();
            message.addReaction(finalVote.up).queue();
            message.addReaction(finalVote.down).queue();
            message.addReaction(finalVote.clear).queue();
            listener.reactionActions.add(new VoteFor(message.getId()));
        });

        listener.votes.add(vote);
    }
}
