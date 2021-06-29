package ch.imgajeed.discordBot.Bot.RandomTeams;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.Person;
import ch.imgajeed.discordBot.Bot.ReactionAction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class TeamAdd extends ReactionAction {
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

        if (emoji.equals("✅") && !Objects.requireNonNull(event.getUser()).isBot()){
            team.persons.add(new Person(event.getUser().getId(), event.getUser().getName()));
            event.getChannel().editMessageById(event.getMessageId(), team.GetMessage()).queue();
        } else if (emoji.equals("\uD83C\uDD97") && !Objects.requireNonNull(event.getUser()).isBot()) {
            event.getChannel().editMessageById(event.getMessageId(), team.GetTeams()).queue();
        }
    }

    @Override
    public void OnReactionRemove(@NotNull MessageReactionRemoveEvent event, Listener listener) {
        var emoji = event.getReactionEmote().getEmoji();
        var team = GetTeam(listener.teams, event.getMessageId());
        assert team != null;

        if (emoji.equals("✅") && !Objects.requireNonNull(event.getUser()).isBot()){
            team.persons.remove(GetPerson(event.getUser(), team.persons));
            event.getChannel().editMessageById(event.getMessageId(), team.GetMessage()).queue();
        } else if (emoji.equals("\uD83C\uDD97") && !Objects.requireNonNull(event.getUser()).isBot()) {
            event.getChannel().editMessageById(event.getMessageId(), team.GetMessage()).queue();
        }
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
