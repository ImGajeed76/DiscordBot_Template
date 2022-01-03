package ch.imgajeed.discordBotTemplate.Bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

//Do not edit!
public class Help extends MessageAction {
    @Override
    public int contentLength() {
        return 0;
    }

    @Override
    public String name() {
        return "Help";
    }

    @Override
    public String[] content() {
        return new String[]{""};
    }

    @Override
    public void Run(@NotNull MessageReceivedEvent event, Listener listener) {
        var messageActions = listener.messageActions;

        try {
            event.getMessage().delete().queue();
        } catch (Exception ignored) {
        }

        var divider = 10;
        var rest = messageActions.size() % divider;

        SendMessageToAuthor(event, "Commands:");

        for (int i = 0; i < (messageActions.size() - rest) / divider; i++) {
            StringBuilder message = new StringBuilder();

            for (int j = 0; j < divider; j++) {
                message.append(" > ").append(listener.prefix).append("**").append(messageActions.get(((i + 1) * j)).name()).append("**   ").append(listener.ContentToString(messageActions.get(((i + 1) * j)).content())).append("\n");
            }

            SendMessageToAuthor(event, message.toString());
        }

        StringBuilder message = new StringBuilder();

        for (int i = 0; i < rest; i++) {
            message.append(" > ").append(listener.prefix).append("**").append(messageActions.get(messageActions.size() - (rest - i)).name()).append("**   ").append(listener.ContentToString(messageActions.get(messageActions.size() - (rest - i)).content())).append("\n");
        }

        SendMessageToAuthor(event, message.toString());
    }

    private void SendMessageToAuthor(@NotNull MessageReceivedEvent event, String message) {
        event.getMessage().getAuthor().openPrivateChannel().complete().sendMessage(message).queue();
    }
}
