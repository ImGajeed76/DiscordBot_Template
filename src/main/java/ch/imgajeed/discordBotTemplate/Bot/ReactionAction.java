package ch.imgajeed.discordBotTemplate.Bot;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

public abstract class ReactionAction {
    public abstract String id();

    public abstract void OnReactionAdd(@NotNull MessageReactionAddEvent event, Listener listener);

    public abstract void OnReactionRemove(@NotNull MessageReactionRemoveEvent event, Listener listener);
}
