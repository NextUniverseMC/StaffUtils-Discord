package ml.nextuniverse.staffutils.discord;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by TheDiamondPicks on 29/07/2017.
 */
public class NextReport {
    static DiscordAPI api;



    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("token.txt"));
            String token = br.readLine();
            api = Javacord.getApi(token, true);
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
        catch (IOException e ) {
            System.err.println("Please create the token.txt file and place the token within it!");
        }
    }
}
