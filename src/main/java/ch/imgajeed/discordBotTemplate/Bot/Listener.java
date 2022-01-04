package ch.imgajeed.discordBotTemplate.Bot;

import ch.imgajeed.discordBotTemplate.Bot.Commands.Examples.Echo;
import ch.imgajeed.discordBotTemplate.Bot.Commands.Examples.RandomNumber;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;

//Do not edit!
public class Listener extends ListenerAdapter {
    public ArrayList<MessageAction> messageActions = new ArrayList<>();
    public ArrayList<ReactionAction> reactionActions = new ArrayList<>();

    public String prefix;
    public JDABuilder builder;
    public Character contentPrefix;

    public int memberCheckTimeOut = 60; // in seconds
    private long lastSecond = 0;

    public ArrayList<Member> totalMembers = new ArrayList<>();
    public boolean showTotalMembers = true;

    private long lastChangeSecond = 0;
    private boolean watching = true;

    public Listener(String prefix, JDABuilder builder, Character contentPrefix) {
        this.prefix = prefix;
        this.builder = builder;
        this.contentPrefix = contentPrefix;

        messageActions.add(new Help());
        AddCommands();
    }

    public Listener(String prefix, JDABuilder builder) {
        this.prefix = prefix;
        this.builder = builder;
        this.contentPrefix = '>';

        messageActions.add(new Help());
        AddCommands();
    }

    public String ContentToString(String[] content) {
        StringBuilder result = new StringBuilder();

        for (var parameter : content) {
            if (!Objects.equals(parameter, "")) {
                result.append(contentPrefix).append("*").append(parameter).append("* ");
            }
        }

        return result.toString();
    }

    public void ContentToShort(MessageChannel channel) {
        channel.sendMessage("> **Error:** Content is to short. Start parameter by using a '" + contentPrefix + "'.").queue();
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
        updateMemberCount(event);
        changeActivity(event);

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
            if (c == contentPrefix) {
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

            if (c == contentPrefix) {
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

    public void updateMemberCount(MessageReceivedEvent event) {
        var currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("Z"));

        if (currentSecond > lastSecond + memberCheckTimeOut) {

            System.out.println("check");
            var guilds = event.getJDA().getGuilds();
            var totalMembers = new ArrayList<String>();
            this.totalMembers = new ArrayList<>();

            for (var guild : guilds) {
                for (var member : guild.getMembers()) {
                    if (!totalMembers.contains(member.getId()) && !member.getUser().isBot() && !member.getUser().getName().equals("PGC Network")) {
                        totalMembers.add(member.getId());
                        this.totalMembers.add(member);
                    }
                }
            }

            event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.WATCHING, totalMembers.size() + " members"));
            lastSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("Z"));
        }
    }

    private void changeActivity(MessageReceivedEvent event) {
        var currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("Z"));

        if (currentSecond > lastChangeSecond + 10) {
            if (watching && showTotalMembers) {
                event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.WATCHING, totalMembers.size() + " members"));
            } else {
                event.getJDA().getPresence().setActivity(Activity.playing(prefix + "help"));
            }
            watching = !watching;
            lastChangeSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("Z"));
        }
    }

    // -------Add your actions here:-------
    private void AddCommands() {
        messageActions.add(new RandomNumber());
        messageActions.add(new Echo());
    }
    // -------------------------------------------------
}
