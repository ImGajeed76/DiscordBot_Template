package ch.imgajeed.discordBot.Bot.Events;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.ReactionAction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class EventAdd extends ReactionAction implements Serializable {
    public String id;

    public EventAdd(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void OnReactionAdd(@NotNull MessageReactionAddEvent reactionAddEvent, Listener listener) {
        var emoji = reactionAddEvent.getReactionEmote().getEmoji();
        System.out.println(emoji);
        var user = reactionAddEvent.getUser();
        if (user == null) {return;}
        var event = GetEvent(reactionAddEvent.getMessageId(), listener);
        assert event != null;

        if (emoji.equals(event.add) && !user.isBot() && !UserInList(user, event.peopleID)) {
            event.peopleName.add(user.getName());
            event.peopleID.add(user.getId());
        }

        if (emoji.equals(event.remove) && !user.isBot() && UserInList(user, event.peopleID)) {
            event.peopleName.remove(GetUserByName(user, event.peopleName));
            event.peopleID.remove(GetUserByID(user, event.peopleID));
        }

        reactionAddEvent.getChannel().editMessageById(reactionAddEvent.getMessageId(), event.GetMessage()).queue();
    }

    @Override
    public void OnReactionRemove(@NotNull MessageReactionRemoveEvent reactionAddEvent, Listener listener) {

    }

    public String GetUserByID(User user, ArrayList<String> peopleID) {
        for (String person : peopleID) {
            if (person.equals(user.getId())) {
                return person;
            }
        }

        return user.getId();
    }

    public String GetUserByName(User user, ArrayList<String> peopleName) {
        for (String person : peopleName) {
            if (person.equals(user.getName())) {
                return person;
            }
        }

        return user.getName();
    }

    public boolean UserInList(User user, ArrayList<String> peopleID) {
        for (String personID : peopleID) {
            if (personID.equals(user.getId())) {
                return true;
            }
        }

        return false;
    }

    public Event GetEvent(String messageID, Listener listener) {
        for (int i = 0; i < listener.events.size(); i++) {
            if (listener.events.get(i).messageID.equals(messageID)) {
                return listener.events.get(i);
            }
        }

        return null;
    }
}
