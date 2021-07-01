package ch.imgajeed.discordBot.Bot.RandomTeams;

import ch.imgajeed.discordBot.Bot.Listener;
import ch.imgajeed.discordBot.Bot.MessageAction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CreateRandomTeam extends MessageAction {
    @Override
    public int contentLength() {
        return 2;
    }

    @Override
    public String name() {
        return "RandomTeams";
    }

    @Override
    public String content() {
        return ":*Title* :*TeamSize*";
    }

    @Override
    public void Run(@NotNull MessageReceivedEvent event, Listener listener) {
        var content = listener.GetContent(event.getMessage().getContentRaw());
        if (content.size() < contentLength()) listener.ContentToShort(event.getChannel());
        var title = content.get(0);
        var teamSize = content.get(1);

        var team = new Team(title, Integer.parseInt(teamSize));

        event.getChannel().sendMessage(team.GetMessage()).queue(message -> {
            team.messageID = message.getId();
            message.addReaction(team.add).queue();
            message.addReaction(team.remove).queue();
            message.addReaction(team.generate).queue();
            message.addReaction(team.back).queue();
            listener.teams.add(team);
            listener.reactionActions.add(new TeamAdd(message.getId()));
        });
    }
}
