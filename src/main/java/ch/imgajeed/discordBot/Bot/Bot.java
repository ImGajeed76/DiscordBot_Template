package ch.imgajeed.discordBot.Bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;

//Do not edit!
public class Bot {
    public static JDABuilder builder;
    public static String prefix;

    public Bot(String token, String prefix) throws FileNotFoundException, JsonProcessingException {
        Bot.prefix = prefix;

        builder = JDABuilder.createDefault(token);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.playing(prefix + "help"));
        //builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.addEventListeners(new Listener(prefix, builder));
    }

    public void Build() throws LoginException {
        builder.build();
    }
}

//maven imports:
//
//    <dependencies>
//        <dependency>
//            <groupId>net.dv8tion</groupId>
//            <artifactId>JDA</artifactId>
//            <version>4.2.0_168</version>
//        </dependency>
//    </dependencies>
//
//    <repositories>
//        <repository>
//            <id>jcenter</id>
//            <name>jcenter-bintray</name>
//            <url>https://jcenter.bintray.com/</url>
//        </repository>
//    </repositories>
