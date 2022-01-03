package ch.imgajeed.discordBotTemplate.Bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

//Do not edit!
public abstract class MessageAction {
    public abstract int contentLength();

    public abstract String name();

    public abstract String[] content();

    public abstract void Run(@NotNull MessageReceivedEvent event, Listener listener);
}
