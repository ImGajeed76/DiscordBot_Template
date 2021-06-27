package ch.imgajeed.discordBot.Bot.Vote;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.ReactionAction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VoteFor extends ReactionAction {
    private final String id;

    public VoteFor(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void OnReactionAdd(@NotNull MessageReactionAddEvent event, Listener listener) {
        var emoji = event.getReactionEmote().getEmoji();
        var vote = GetVote(event.getMessageId(), listener);
        assert vote != null;

        if (emoji.equals("⬆") && !Objects.requireNonNull(event.getUser()).isBot()) {
            vote.upVotes += 1;
        } else if (emoji.equals("⬇") && !Objects.requireNonNull(event.getUser()).isBot()) {
            vote.downVotes += 1;
        }

        event.getChannel().editMessageById(event.getMessageId(), vote.GetMessage()).queue();
    }

    @Override
    public void OnReactionRemove(@NotNull MessageReactionRemoveEvent event, Listener listener) {
        var emoji = event.getReactionEmote().getEmoji();
        var vote = GetVote(event.getMessageId(), listener);
        assert vote != null;

        if (emoji.equals("⬆") && !Objects.requireNonNull(event.getUser()).isBot()) {
            vote.upVotes -= 1;
        } else if (emoji.equals("⬇") && !Objects.requireNonNull(event.getUser()).isBot()) {
            vote.downVotes -= 1;
        }

        event.getChannel().editMessageById(event.getMessageId(), vote.GetMessage()).queue();
    }

    private Vote GetVote(String messageID, Listener listener) {
        for (int i = 0; i < listener.votes.size(); i++) {
            if (listener.votes.get(i).messageID.equals(messageID)) {
                return listener.votes.get(i);
            }
        }

        return null;
    }
}
