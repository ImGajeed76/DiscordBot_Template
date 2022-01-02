package ch.imgajeed.discordBot.Bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

//Do not edit!
public class Listener extends ListenerAdapter {
    public ArrayList<MessageAction> messageActions = new ArrayList<>();
    public ArrayList<ReactionAction> reactionActions = new ArrayList<>();

    public String prefix;
    public JDABuilder builder;

    public static String path = "data.json";

    public Listener(String prefix, JDABuilder builder) {
        this.prefix = prefix;
        this.builder = builder;

        messageActions.add(new Help());
    }

    public void ContentToShort(MessageChannel channel) {
        channel.sendMessage("> **Error:** Content is to short. Start parameter by using a ':'.").queue();
    }

    private boolean isPrivateChanel(ChannelType channelType, MessageChannel channel, User user) {
        if (channelType.equals(ChannelType.PRIVATE) && !user.isBot()) {
            channel.sendMessage("** This Bot does not work in private channels! **").queue();
            return true;
        }

        return false;
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        if (isPrivateChanel(event.getChannelType(), event.getChannel(), event.getUser())) {
            return;
        }

        var reactionAction = GetReactionAction(reactionActions, event.getMessageId());
        if (reactionAction != null) {
            reactionAction.OnReactionRemove(event, this);
        }
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (isPrivateChanel(event.getChannelType(), event.getChannel(), event.getUser())) {
            return;
        }

        var reactionAction = GetReactionAction(reactionActions, event.getMessageId());
        if (reactionAction != null) {
            reactionAction.OnReactionAdd(event, this);
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();
        if (!IncludesPrefix(message)) {
            return;
        }

        message = ExcludePrefix(message);

        var action = GetMessageAction(messageActions, GetMessageAction(message));
        if (action != null) {
            action.Run(event, this);
        }
    }

    private ReactionAction GetReactionAction(ArrayList<ReactionAction> reactionActions, String id) {
        for (ReactionAction reactionAction : reactionActions) {
            if (reactionAction.id().equals(id)) {
                return reactionAction;
            }
        }

        return null;
    }

    private MessageAction GetMessageAction(ArrayList<MessageAction> messageActions, String actionName) {
        for (MessageAction messageAction : messageActions) {
            if (messageAction.name().toLowerCase().equals(actionName)) {
                return messageAction;
            }
        }

        return null;
    }

    private String ExcludePrefix(String message) {
        var m = message.toCharArray();
        var prefix = this.prefix.toCharArray();

        StringBuilder newMessage = new StringBuilder();

        if (m.length >= prefix.length) {
            for (int i = 0; i < m.length - prefix.length; i++) {
                newMessage.append(m[i + prefix.length]);
            }
        }

        return newMessage.toString();
    }

    private String GetMessageAction(String message) {
        var m = message.toCharArray();

        StringBuilder newMessage = new StringBuilder();

        for (char c : m) {
            if (c == ' ') {
                return newMessage.toString();
            } else {
                newMessage.append(c);
            }
        }

        return newMessage.toString();
    }

    private boolean IncludesPrefix(String message) {
        var m = message.toCharArray();
        var prefix = this.prefix.toCharArray();

        if (m.length >= prefix.length) {
            for (int i = 0; i < prefix.length; i++) {
                if (m[i] != prefix[i]) {
                    return false;
                }
            }
        }

        return true;
    }

    public ArrayList<String> GetContent(String message) {
        var m = message.toCharArray();
        var write = false;
        var pos = -1;

        ArrayList<StringBuilder> content = new ArrayList<>();

        for (char c : m) {
            if (c == ':') {
                write = false;
                if (pos > -1) {
                    if (content.get(pos).length() > 1) {
                        content.get(pos).replace(content.get(pos).length() - 1, content.get(pos).length(), "");
                    }
                }
            }

            if (write) {
                if (pos + 1 > content.size()) {
                    content.add(new StringBuilder());
                }
                content.get(pos).append(c);
            }

            if (c == ':') {
                write = true;
                pos++;
            }
        }

        ArrayList<String> stringContent = new ArrayList<>();

        for (StringBuilder stringBuilder : content) {
            stringContent.add(stringBuilder.toString());
        }

        return stringContent;
    }
}
