package ch.imgajeed.discordBot.Bot.Events;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.MessageAction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CreateEvent extends MessageAction {
    @Override
    public int contentLength() {
        return 2;
    }

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
        if (content.size() < contentLength()) listener.ContentToShort(receivedEvent.getChannel());

        var event = new Event(content.get(0), content.get(1));

        receivedEvent.getChannel().sendMessage(event.GetMessage()).queue(message -> {
            event.messageID = message.getId();
            message.addReaction(event.add).queue();
            message.addReaction(event.remove).queue();
            listener.reactionActions.add(new EventAdd(message.getId()));
        });

        listener.events.add(event);
    }
}
