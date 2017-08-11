package org.nulldev.bungeecommands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin implements Listener {
	
	  public static Main instance;
	  public static Main getInstance() {
			return instance;
		}
	  public static File configFile;
	  public List<String> voteMessage;
	  public List<String> reloadMessage;
	  public List<String> websiteMessage;
	  public List<String> discordMessage;
	  public List<String> helpMessage;
	  public static ConfigurationProvider configurationProvider;
	  
	  static { configurationProvider = ConfigurationProvider.getProvider(YamlConfiguration.class); }
	  private final static int CENTER_PX = 154;
		 
		@SuppressWarnings("deprecation")
		public static void sendCenteredMessage(ProxiedPlayer player, String message){
		        if(message == null || message.equals("")) player.sendMessage("");
		                message = ChatColor.translateAlternateColorCodes('&', message);
		               
		                int messagePxSize = 0;
		                boolean previousCode = false;
		                boolean isBold = false;
		               
		                for(char c : message.toCharArray()){
		                        if(c == '§'){
		                                previousCode = true;
		                                continue;
		                        }else if(previousCode == true){
		                                previousCode = false;
		                                if(c == 'l' || c == 'L'){
		                                        isBold = true;
		                                        continue;
		                                }else isBold = false;
		                        }else{
		                                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
		                                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
		                                messagePxSize++;
		                        }
		                }
		               
		                int halvedMessageSize = messagePxSize / 2;
		                int toCompensate = CENTER_PX - halvedMessageSize;
		                int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		                int compensated = 0;
		                StringBuilder sb = new StringBuilder();
		                while(compensated < toCompensate){
		                        sb.append(" ");
		                        compensated += spaceLength;
		                }
		                player.sendMessage(sb.toString() + message);
		        }
	  @Override
	  public void onEnable() {
	    instance = this;
	    try {
	      this.createConfig();
	      this.loadConfig();
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	      return;
	    }
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new VoteCMD());
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new WebsiteCMD());
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new DiscordCMD());
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReloadConfigCMD());
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new HelpCMD());
	    this.getProxy().getPluginManager().registerListener(this, this);
	  }
	  
	  public void createConfig() throws IOException {
	    List<String> votedefault = new ArrayList<String>();
	    votedefault.add("&a&m&l=============================================");
	    votedefault.add("");
	    votedefault.add("&a&lVote links: https://epticmc.com/vote");
	    votedefault.add("");
	    votedefault.add("&a&m&l=============================================");
	    List<String> reloaddefault = new ArrayList<String>();
	    reloaddefault.add("&a&m&l=============================================");
	    reloaddefault.add("");
	    reloaddefault.add("&a&lConfig reloaded");
	    reloaddefault.add("");
	    reloaddefault.add("&a&m&l=============================================");
	    List<String> websitedefault = new ArrayList<String>();
	    websitedefault.add("&a&m&l=============================================");
	    websitedefault.add("");
	    websitedefault.add("&a&lWebsite: https://epticmc.com/");
	    websitedefault.add("");
	    websitedefault.add("&a&m&l=============================================");
	    List<String> discorddefault = new ArrayList<String>();
	    discorddefault.add("&a&m&l=============================================");
	    discorddefault.add("");
	    discorddefault.add("&a&Discord: https://discord.gg/7f3bwfX");
	    discorddefault.add("");
	    discorddefault.add("&a&m&l=============================================");
	    List<String> helpdefault = new ArrayList<String>();
	    helpdefault.add("&a&m&l=============================================");
	    helpdefault.add("");
	    helpdefault.add("&a&lHelp:");
	    helpdefault.add("");
	    helpdefault.add("&a&m&l=============================================");
	    File folder = new File(this.getDataFolder().getPath());
	    if (!folder.exists()) folder.mkdir();
	    if (!(Main.configFile = new File(this.getDataFolder(), "config.yml")).exists()) configFile.createNewFile();
	    Configuration config = configurationProvider.load(configFile);
	    this.addDefault(config, "VoteMessage", votedefault);
	    this.addDefault(config, "ReloadMessage", reloaddefault);
	    this.addDefault(config, "WebsiteMessage", websitedefault);
	    this.addDefault(config, "DiscordMessage", discorddefault);
	    this.addDefault(config, "HelpMessage", helpdefault);
	    configurationProvider.save(config, configFile);
	  }

	  private void addDefault(Configuration config, String path, Object value) { if (!config.contains(path)) config.set(path, value); }
	  
	  
	  public void loadConfig() throws IOException {
	    Configuration config = configurationProvider.load(configFile);
	    voteMessage = config.getStringList("VoteMessage");
	    websiteMessage = config.getStringList("WebsiteMessage");
	    reloadMessage = config.getStringList("ReloadMessage");
	    discordMessage = config.getStringList("DiscordMessage");
	    helpMessage = config.getStringList("HelpMessage");
	  }
}
