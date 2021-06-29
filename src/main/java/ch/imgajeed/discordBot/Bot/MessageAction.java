package ch.imgajeed.discordBot.Bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

//Do not edit!
public abstract class MessageAction {
    public abstract String name();
    public abstract String content();

    public abstract void Run(@NotNull MessageReceivedEvent event, Listener listener);
}
