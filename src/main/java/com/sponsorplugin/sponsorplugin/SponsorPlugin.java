// bitcoinjake09 - sponsorplugin - 09-25-2021
package com.sponsorplugin.sponsorplugin;

import com.sponsorplugin.sponsorplugin.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;



public class SponsorPlugin extends JavaPlugin implements Listener {

  public boolean maintenance_mode = false;
  public static final List<sponsor> sponsorList = new ArrayList<>();
  String welcomeMsg;

  public static final String PLUGIN_WEBSITE = "https://github.com/BitcoinJake09/SponsorPlugin";

  public void onEnable() {
    log("[SponsorPlugin] SponsorPlugin starting");
    try {
      System.out.println("[SponsorPlugin] Starting SponsorPlugin");
      getServer().getPluginManager().registerEvents(this, this);
            // loads config file. If it doesn't exist, creates it.
      getDataFolder().mkdir();
      System.out.println("[SponsorPlugin] checking default config file");

      if (!new java.io.File(getDataFolder(), "config.yml").exists()) {
        saveDefaultConfig();
        System.out.println("[SponsorPlugin] config file does not exist. creating default.");
      }
      //sponsorList = new ArrayList<>();
  	loadJSON();

      System.out.println("[SponsorPlugin] finished");

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("[SponsorPlugin] plugin enable fails");
      Bukkit.shutdown();
    }
  }


  public void loadJSON() {
  JSONParser jsonParser = new JSONParser();
  System.out.println("[SponsorPlugin] attempting to load json config files.");
          try {
		File configFile = new File(System.getProperty("user.dir") + "/plugins/SponsorPlugin/config.json");

            FileReader reader = new FileReader(configFile);

            Object obj = jsonParser.parse(reader);
            JSONArray configData = (JSONArray) obj;
	configData.forEach( tConfig -> parseJSON( (JSONObject) tConfig) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           System.out.println("[SponsorPlugin] no config json files found in directory plugins/SponsorPlugin/* .");
        } catch (IOException e) {
	   System.out.println("[SponsorPlugin] error reading json files.");
            e.printStackTrace();
        } catch (ParseException e) {
        System.out.println("[SponsorPlugin] error parsing json files.");
            e.printStackTrace();
        }
  }

   private static void parseJSON(JSONObject tConfig) {
   try {

   	JSONObject configDataObj = (JSONObject) tConfig.get("config");
   	JSONObject sponsorsJsons = (JSONObject) configDataObj.get("SPONSOR1");
	int x = 1;

	while (sponsorsJsons != null) {
    sponsor tempSponsor = new sponsor((String) sponsorsJsons.get("NAME").toString(), (String) sponsorsJsons.get("MSG").toString());
sponsorList.add(tempSponsor);
System.out.println("[SponsorPlugin] [ADD] Sponsor: " + tempSponsor.NAME + " / " + tempSponsor.MSG);
   	x = x+1;
	sponsorsJsons = (JSONObject) configDataObj.get("SPONSOR" + x);
   	}
            } catch (Exception e) {
      e.printStackTrace();
      System.out.println("[SponsorPlugin] [fatal] plugin enable failed to get config.json");
    }
  }

  public static void announce(final String message) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(message);
    }
  }

  public void log(String msg) {
    Bukkit.getLogger().info(msg);
  }
  public void updateScoreboard(final Player player) throws ParseException, org.json.simple.parser.ParseException, IOException {
                  ScoreboardManager scoreboardManager;
                  Scoreboard playSBoard;
                  Objective playSBoardObj;
                  scoreboardManager = Bukkit.getScoreboardManager();
                  playSBoard= scoreboardManager.getNewScoreboard();
                  playSBoardObj = playSBoard.registerNewObjective("Sponsor","dummy");

                  playSBoardObj.setDisplaySlot(DisplaySlot.SIDEBAR);

                  playSBoardObj.setDisplayName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Sponsor " + ChatColor.GOLD + ChatColor.BOLD.toString() + "List");
                  int scoreSet= (sponsorList.size()-1) * 2;
                  Score score;
                  for (int x=0;x<sponsorList.size();x++) {
                    score = playSBoardObj.getScore(ChatColor.GREEN + sponsorList.get(x).NAME);
                    score.setScore(scoreSet);
                    scoreSet--;
                    score = playSBoardObj.getScore(ChatColor.GOLD + sponsorList.get(x).MSG);
                    score.setScore(scoreSet);
                  }
        		  player.setScoreboard(playSBoard);
      }
        @EventHandler(priority = EventPriority.HIGHEST)
        public void onPlayerJoin(PlayerJoinEvent event) throws ParseException{
                  Player p = event.getPlayer();
                  final Long sponsorLong = 100L;
                    try {
                      Bukkit.getScheduler().runTaskLater(this, () -> {
                      p.sendTitle(ChatColor.GREEN + "Welcome, ", ChatColor.AQUA + p.getDisplayName(), 10, 70, 20);
                      for (int x=0;x<sponsorList.size();x++) {
                        final Long tempsponsorLong = (Long) sponsorLong * (x+1);
                        final int tempX = x;
                        Bukkit.getScheduler().runTaskLater(this, () -> {
                        System.out.println("[SponsorPlugin] Sponsor: " + sponsorList.get(tempX).NAME + " / " + sponsorList.get(tempX).MSG);
                        // String title, String subtitle, int fadeIn, int stay, int fadeOut.
                        p.sendTitle(ChatColor.GREEN + "Sponsor: ", ChatColor.GREEN + sponsorList.get(tempX).NAME + " / " + sponsorList.get(tempX).MSG, 10, 70, 20);
                      }, tempsponsorLong);
                      }
                    }, 160L);

                  updateScoreboard(p);

                } catch (Exception e){}
        }
} // EOF
