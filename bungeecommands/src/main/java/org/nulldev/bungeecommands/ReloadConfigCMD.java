package org.nulldev.bungeecommands;

import java.io.IOException;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReloadConfigCMD extends Command {
	Main settings = Main.getInstance();
	public ReloadConfigCMD()
	{
	  super("configreload");
	}
	@SuppressWarnings("static-access")
	public void execute(CommandSender sender, String[] args)
	{
	  ProxiedPlayer p = (ProxiedPlayer)sender;
	  for (String message : settings.reloadMessage) {
		  message = ChatColor.translateAlternateColorCodes('&', message);
		  settings.sendCenteredMessage(p, message);
		  try {
			settings.loadConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	    
	    return;
	    }
}
