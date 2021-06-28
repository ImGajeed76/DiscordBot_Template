package ch.imgajeed.discordBot;

import ch.imgajeed.discordBot.Bot.Bot;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws LoginException, FileNotFoundException, JsonProcessingException {
        var bot = new Bot("ODU4NDI0ODk3MDMyOTQ1NjY0.YNd8fA.SlPSP9QLDyGri--WzP-ssp_2yg4", "-");
        bot.Build();
    }
}
