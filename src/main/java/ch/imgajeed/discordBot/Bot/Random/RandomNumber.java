package ch.imgajeed.discordBot.Bot.Random;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.MessageAction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RandomNumber extends MessageAction {
    @Override
    public String name() {
        return "RandomNumber";
    }

    @Override
    public String content() {
        return ":*max(0 - max)*";
    }

    @Override
    public void Run(@NotNull MessageReceivedEvent event, Listener listener) {
        var content = listener.GetContent(event.getMessage().getContentRaw());
        var bounds = Integer.parseInt(content.get(0)) + 1;

        Random random = new Random();
        var number = (random.nextInt(bounds));
        event.getChannel().sendMessage(String.valueOf(number)).queue();
    }
}
