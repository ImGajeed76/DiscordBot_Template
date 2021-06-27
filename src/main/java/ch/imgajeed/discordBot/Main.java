package ch.imgajeed.discordBot;

import ch.imgajeed.discordBot.Bot.Bot;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        var bot = new Bot("ODU4NDI0ODk3MDMyOTQ1NjY0.YNd8fA.SlPSP9QLDyGri--WzP-ssp_2yg4", "-");
        bot.Build();
    }
}
