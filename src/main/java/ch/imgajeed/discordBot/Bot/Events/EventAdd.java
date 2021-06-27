package ch.imgajeed.discordBot.Bot.Events;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.ReactionAction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EventAdd extends ReactionAction {
    public String id;

    public EventAdd(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void OnReactionAdd(@NotNull MessageReactionAddEvent reactionAddEvent, Listener listener) {
        var emoji = reactionAddEvent.getReactionEmote().getEmoji();
        var event = GetEvent(reactionAddEvent.getMessageId(), listener);
        assert event != null;

        if (emoji.equals("✅") && !Objects.requireNonNull(reactionAddEvent.getUser()).isBot()) {
            event.people.add(reactionAddEvent.getUser());
        }

        reactionAddEvent.getChannel().editMessageById(reactionAddEvent.getMessageId(), event.GetMessage()).queue();
    }

    @Override
    public void OnReactionRemove(@NotNull MessageReactionRemoveEvent reactionAddEvent, Listener listener) {
        var emoji = reactionAddEvent.getReactionEmote().getEmoji();
        var event = GetEvent(reactionAddEvent.getMessageId(), listener);
        assert event != null;

        if (emoji.equals("✅") && !Objects.requireNonNull(reactionAddEvent.getUser()).isBot()) {
            event.people.remove(reactionAddEvent.getUser());
        }

        reactionAddEvent.getChannel().editMessageById(reactionAddEvent.getMessageId(), event.GetMessage()).queue();
    }

    private Event GetEvent(String messageID, Listener listener) {
        for (int i = 0; i < listener.events.size(); i++) {
            if (listener.events.get(i).messageID.equals(messageID)) {
                return listener.events.get(i);
            }
        }

        return null;
    }
}
