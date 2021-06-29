package ch.imgajeed.discordBot.Bot.Events;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.MessageAction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CreateEvent extends MessageAction {
    @Override
    public String name() {
        return "CreateEvent";
    }

    @Override
    public String content() {
        return ":*Name* :*Time*";
    }

    @Override
    public void Run(@NotNull MessageReceivedEvent receivedEvent, Listener listener) {
        var content = listener.GetContent(receivedEvent.getMessage().getContentRaw());
        var event = new Event(content.get(0), content.get(1));

        receivedEvent.getChannel().sendMessage(event.GetMessage()).queue(message -> {
            event.messageID = message.getId();
            message.addReaction("✅").queue();
            listener.reactionActions.add(new EventAdd(message.getId()));
        });

        listener.events.add(event);
    }
}
