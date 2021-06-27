package ch.imgajeed.discordBot.Bot;

import ch.imgajeed.discordBot.Bot.Events.CreateEvent;
import ch.imgajeed.discordBot.Bot.Events.Event;
import ch.imgajeed.discordBot.Bot.Vote.CreateVote;
import ch.imgajeed.discordBot.Bot.Vote.Vote;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//Do not edit!
public class Listener extends ListenerAdapter {
    public ArrayList<MessageAction> messageActions = new ArrayList<>();
    public ArrayList<ReactionAction> reactionActions = new ArrayList<>();

    public ArrayList<Vote> votes = new ArrayList<>();
    public ArrayList<Event> events = new ArrayList<>();

    public String prefix;
    public JDABuilder builder;

    public Listener(String prefix, JDABuilder builder) {
        this.prefix = prefix;
        this.builder = builder;

        messageActions.add(new Help());
        messageActions.add(new CreateEvent());
        messageActions.add(new CreateVote());
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        var reactionAction = GetReactionAction(reactionActions, event.getMessageId());
        if (reactionAction != null) {
            reactionAction.OnReactionRemove(event, this);
        }
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
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
