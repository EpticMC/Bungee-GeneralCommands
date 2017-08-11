package org.nulldev.bungeecommands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HelpCMD extends Command {
	Main settings = Main.getInstance();
	public HelpCMD()
	{
	  super("help", "epticmc.help", new String[] { "?", "ehelp" });
	}
	
	@SuppressWarnings("static-access")
	public void execute(CommandSender sender, String[] args)
	{
	  ProxiedPlayer p = (ProxiedPlayer)sender;
	  for (String message : settings.helpMessage) {
		  message = ChatColor.translateAlternateColorCodes('&', message);
		  settings.sendCenteredMessage(p, message);
		}
	    
	    return;
	    }
}
