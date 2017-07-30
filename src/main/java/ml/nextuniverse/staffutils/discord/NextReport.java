package ml.nextuniverse.staffutils.discord;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.awt.*;

/**
 * Created by TheDiamondPicks on 29/07/2017.
 */
public class NextReport {
    static DiscordAPI api;



    public static void main(String[] args) {

        api = Javacord.getApi("MzQwNjU5OTE3MzA2MzMxMTM2.DF1v3A.7puBe4luQfCIaO81kvlwJYAACqc", true);
        api.connectBlocking();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        final Jedis subscriberJedis = jedisPool.getResource();
        final Subscriber subscriber = new Subscriber();

        new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("Subscribing to \"StaffUtils\". This thread will be blocked.");
                    subscriberJedis.subscribe(subscriber, "StaffUtils");
                    System.out.println("Subscription ended.");
                } catch (Exception e) {
                    System.out.println("Subscribing failed." + e);
                }
            }
        }).start();

    }
}
