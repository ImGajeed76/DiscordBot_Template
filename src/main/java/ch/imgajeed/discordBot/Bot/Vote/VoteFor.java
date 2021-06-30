package ch.imgajeed.discordBot.Bot.Vote;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.ReactionAction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class VoteFor extends ReactionAction implements Serializable {
    public final String id;

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

        if (!HasVoted(event.getUser(), vote.votes) && !Objects.requireNonNull(event.getUser()).isBot()) {
            if (emoji.equals(vote.up)) {
                vote.votes.add(new VotePerson(true, event.getUser().getId()));
            } else if (emoji.equals(vote.down)) {
                vote.votes.add(new VotePerson(false, event.getUser().getId()));
            }
        }

        if (HasVoted(event.getUser(), vote.votes) && !Objects.requireNonNull(event.getUser()).isBot()) {
            if (emoji.equals(vote.clear)) {
                vote.votes.remove(GetPerson(true, event.getUser(), vote.votes));
                vote.votes.remove(GetPerson(false, event.getUser(), vote.votes));
            }
        }

        event.getChannel().editMessageById(event.getMessageId(), vote.GetMessage()).queue();
    }

    @Override
    public void OnReactionRemove(@NotNull MessageReactionRemoveEvent event, Listener listener) {

    }

    public VotePerson GetPerson(boolean upVote, User user, ArrayList<VotePerson> votes) {
        for (VotePerson person : votes) {
            if (person.userID.equals(user.getId())) {
                return person;
            }
        }

        return new VotePerson(upVote, user.getId());
    }

    public Vote GetVote(String messageID, Listener listener) {
        for (int i = 0; i < listener.votes.size(); i++) {
            if (listener.votes.get(i).messageID.equals(messageID)) {
                return listener.votes.get(i);
            }
        }

        return null;
    }

    public boolean HasVoted(User user, ArrayList<VotePerson> votes) {
        for (VotePerson person : votes) {
            if (person.userID.equals(user.getId())) {
                return true;
            }
        }

        return false;
    }
}
