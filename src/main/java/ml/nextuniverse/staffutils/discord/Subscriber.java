package ml.nextuniverse.staffutils.discord;

import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import redis.clients.jedis.JedisPubSub;

import java.awt.*;

import static ml.nextuniverse.staffutils.discord.NextReport.api;


public class Subscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        if (channel.equals("StaffUtils")) {
            String[] args = message.split(";");
            if (args[0].equals("DiscordReport")) {
                String target = args[1];
                String reason = args[2];
                String reporter = args[3];
                EmbedBuilder report  = new EmbedBuilder()
                        .setTitle("A report has been created on the server")
                        .setDescription("A user has created a report on the server and either no staff are online or the report has been unclaimed for 10 minutes.")
                        .setColor(new Color(0x8543E9))
                        .setFooter("This message was sent because there are no staff online / the report has remained unclaimed for 10 minutes", null)
                        .setThumbnail("https://discordapp.com/assets/b04ecfe13d61a869b4c47a276b51b634.svg")
                        .addField("User Reported", target, false)
                        .addField("Report Reason", reason, false)
                        .addField("Reported By", reporter, false);
                api.getServerById("311008027064926208").getChannelById("340688317790027776").sendMessage("", report);
            }
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

    }
}

