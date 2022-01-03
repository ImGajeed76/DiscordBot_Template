package ch.imgajeed.discordBot;

import ch.imgajeed.discordBot.Bot.Bot;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws LoginException, FileNotFoundException, JsonProcessingException {
        var bot = new Bot("OTI3MzI3ODk2MTI3ODE5Nzc2.YdInZw.6BoyvuvAlPg7RVPKR208mi90KOo", "-");
        bot.Build();
    }
}
