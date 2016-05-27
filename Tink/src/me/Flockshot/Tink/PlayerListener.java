package me.Flockshot.Tink;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

public class PlayerListener implements Listener
{

	Main plugin;

	
	public PlayerListener(Main main)
	{
		plugin = main;
	}

@EventHandler
public void onCmd(PlayerCommandPreprocessEvent event)
{
	Player player = event.getPlayer();
	Player plr;
	String[] name;
	String msg = event.getMessage();
	
	File file = new File(plugin.getDataFolder(), "TinkToggle.yml");
	FileConfiguration f = YamlConfiguration.loadConfiguration(file);
	
	Essentials e = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	User user = e.getUser(player);
	User u;
	
	Sound s = Sound.valueOf((String) plugin.getConfig().get("SoundName"));
	float v = plugin.getConfig().getInt("Volume");
	float p = plugin.getConfig().getInt("Pitch");	
	boolean toggled;	
	
	if(msg.startsWith("/msg") || msg.startsWith("/w") || msg.startsWith("/mail") || msg.startsWith("/m") ||  msg.startsWith("/emsg") ||  msg.startsWith("/whisper") ||  msg.startsWith("/tell") ||  msg.startsWith("/email") ||  msg.startsWith("/ewhisper") ||  msg.startsWith("/etell") || msg.startsWith("/t") )
	{
		
		
		if(!user.isMuted())
		{
			if(player.hasPermission("essentials.msg"))
			{
				if(msg.startsWith("/msg"))
				msg = msg.replace("/msg ", "");
				else if(msg.startsWith("/w"))
				msg = msg.replace("/w ", "");
				else if(msg.startsWith("/mail"))
				msg = msg.replace("/mail ", "");
				else if(msg.startsWith("/m"))
				msg = msg.replace("/m ", "");
				else if(msg.startsWith("/whisper"))
				msg = msg.replace("/whisper ", "");
				else if(msg.startsWith("/emsg"))
				msg = msg.replace("/emsg ", "");
				else if(msg.startsWith("/tell"))
				msg = msg.replace("/tell ", "");
				else if(msg.startsWith("/email"))
				msg = msg.replace("/email ", "");
				else if(msg.startsWith("/ewhisper"))
				msg = msg.replace("/ewhisper ", "");
				else if(msg.startsWith("/etell"))
				msg = msg.replace("/etell ", "");
				else if(msg.startsWith("/t"))
				msg = msg.replace("/t ", "");

				name = msg.split(" ");
				
				if(name[0].trim().length() >= 2)
				{
					plr = Bukkit.getServer().getPlayer(name[0]);			
					
					if(plr!=null)
					{
						u = e.getUser(plr);
						if(u!=null)
						{
							if((!e.getVanishedPlayers().contains(plr.getName()) && !u._getIgnoredPlayers().contains(player.getName()) ) || player.isOp())
							{
								toggled = f.getBoolean(plr.getName());
								if(s!=null)
								{
									if(toggled)
									{								
										plr.playSound(plr.getLocation(), s, v, p);		
									}
								}
							}
						}						
					}
				}
			}
		}
	}
			
	else if(msg.startsWith("/r") || msg.startsWith("/er") || msg.startsWith("/reply"))
	{
		
		if(player.hasPermission("essentials.msg"))
		{				
			plr = user.getReplyTo().getPlayer();
					
			player.sendMessage("Outside plr!=null");
			if(plr!=null)
			{
						
				u = e.getUser(plr);
				if(u!=null)
				{
					if((!e.getVanishedPlayers().contains(plr.getName()) && !u._getIgnoredPlayers().contains(player.getName())) || player.isOp())
					{
						toggled = f.getBoolean(plr.getName());
						if(s!=null)
						{
							if(toggled)
							{								
								plr.playSound(plr.getLocation(), s, v, p);		
							}
						}
					}
				}						
			}					
		}				
	}		
}

@EventHandler
public void onJoin(PlayerJoinEvent event) throws IOException
{
	File file = new File(plugin.getDataFolder(), "TinkToggle.yml");
	FileConfiguration f = YamlConfiguration.loadConfiguration(file);
	
	Player player = event.getPlayer();
	if(!f.contains(player.getName()))
	{
		f.set(player.getName(), plugin.getConfig().get("ToggleonFirstJoin"));
		f.save(file);
	}
	
	
}
	
	
	
}
