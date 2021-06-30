package ch.imgajeed.discordBot.Bot.RandomTeams;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.Person;
import ch.imgajeed.discordBot.Bot.ReactionAction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class TeamAdd extends ReactionAction implements Serializable {
    public String messageID;

    public TeamAdd(String messageID) {
        this.messageID = messageID;
    }

    @Override
    public String id() {
        return messageID;
    }

    @Override
    public void OnReactionAdd(@NotNull MessageReactionAddEvent event, Listener listener) {
        var emoji = event.getReactionEmote().getEmoji();
        var team = GetTeam(listener.teams, event.getMessageId());
        assert team != null;


        if (emoji.equals(team.add) && !Objects.requireNonNull(event.getUser()).isBot() && !IsPersonInList(event.getUser(), team.persons)) {
            team.persons.add(new Person(event.getUser().getId(), event.getUser().getName()));
            event.getChannel().editMessageById(event.getMessageId(), team.GetMessage()).queue();
        } else if (emoji.equals(team.generate) && !Objects.requireNonNull(event.getUser()).isBot()) {
            event.getChannel().editMessageById(event.getMessageId(), team.GetTeams()).queue();
        } else if (emoji.equals(team.remove) && !Objects.requireNonNull(event.getUser()).isBot() && IsPersonInList(event.getUser(), team.persons)) {
            team.persons.remove(GetPerson(event.getUser(), team.persons));
            event.getChannel().editMessageById(event.getMessageId(), team.GetMessage()).queue();
        } else if (emoji.equals(team.back) && !Objects.requireNonNull(event.getUser()).isBot()) {
            event.getChannel().editMessageById(event.getMessageId(), team.GetMessage()).queue();
        }
    }

    @Override
    public void OnReactionRemove(@NotNull MessageReactionRemoveEvent event, Listener listener) {

    }

    private boolean IsPersonInList(User user, ArrayList<Person> persons) {
        for (Person person : persons) {
            if (person.userID.equals(user.getId())) {
                return true;
            }
        }

        return false;
    }

    private Person GetPerson(User user, ArrayList<Person> persons) {
        for (Person person : persons) {
            if (person.userID.equals(user.getId())) {
                return person;
            }
        }

        return new Person(user.getId(), user.getName());
    }

    private Team GetTeam(ArrayList<Team> teams, String messageID) {
        for (Team team : teams) {
            if (team.messageID.equals(messageID)) {
                return team;
            }
        }

        return null;
    }
}
