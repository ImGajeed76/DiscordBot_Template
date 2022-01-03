package ch.imgajeed.discordBot;

import ch.imgajeed.discordBot.Bot.Bot;

import javax.security.auth.login.LoginException;
import java.io.File;

public class Main {
    public static void main(String[] args) throws LoginException {
        var tokenFile = new File("token.txt");
        var bot = new Bot(tokenFile, "-");
        bot.Build();
    }
}
