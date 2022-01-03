package ch.imgajeed.discordBotTemplate.Bot.Commands.Examples;

import ch.imgajeed.discordBotTemplate.Bot.Listener;
import ch.imgajeed.discordBotTemplate.Bot.MessageAction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class Echo extends MessageAction {
    @Override
    public int contentLength() {
        return 1;
    }

    @Override
    public String name() {
        return "echo";
    }

    @Override
    public String[] content() {
        return new String[]{"text"};
    }

    @Override
    public void Run(@NotNull MessageReceivedEvent event, Listener listener) {
        var content = listener.GetContent(event.getMessage().getContentRaw());

        event.getChannel().sendMessage("> " + content.get(0)).queue();
    }
}
