package me.Flockshot.Tink;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class Commands
{
	public static Main plugin = null;


	public Commands(Main main)
	{
		plugin = main;
	}

	public static void ProcessCommand(CommandSender sender, Command command, String cmd, String[] args) throws IOException
	{
		File file = new File(plugin.getDataFolder(), "TinkToggle.yml");
		FileConfiguration f = YamlConfiguration.loadConfiguration(file);
		
		cmd = cmd.toLowerCase();
		
		if(cmd.equals("toggletink"))
		{
			if(sender.hasPermission("tink.toggle"))
			{
				String name = null;
				boolean t = true;
				if(args.length==0 && sender instanceof Player)
				{
					name = sender.getName();
					t = (boolean) f.get(name);
					
					if(t==true)
						t=false;
					else if(t==false)
						t=true;		
				}
				
				if(args.length>=1 )
				{
					if(sender.hasPermission("tink.admin.toggle"))
					{
						if(Bukkit.getPlayer(args[0])!=null)
						{
							name = Bukkit.getPlayer(args[0]).getName();							
							t = (boolean) f.get(name);
							
							if(t==true)
								t=false;
							else if(t==false)
								t=true;
							
						}
						else sender.sendMessage(ChatColor.RED + "Error" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + "Player not found.");												
					}
					
					else
					{
						name = sender.getName();
						
						String tog = args[0];
						if(tog.equals("true") || tog.equals("on") || tog.equals("enable"))
						{
							t=true;
						}
						else if(tog.equals("false") || tog.equals("off") || tog.equals("disable"))
						{
							t=false;
						}
						else
						{
							t = (boolean) f.get(name);
							
							if(t==true)
								t=false;
							else if(t==false)
								t=true;
						}
					}						
				}
				
				if(name!=null)
				{
					f.set(name, t);
					f.save(file);
					String s;
					
					if(t)
						s="enabled";
					else
						s="disabled";					
					sender.sendMessage(ChatColor.GRAY + "Set Message Notifier " + ChatColor.YELLOW + s + ChatColor.GRAY + " for " +Bukkit.getPlayer(name).getDisplayName() );
				}
			
				
			}
			
		}
		else if(cmd.equals("tink"))
		{
			PluginDescriptionFile p = plugin.getDescription();
			sender.sendMessage(ChatColor.YELLOW + "Plugin" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + p.getName());
			sender.sendMessage(ChatColor.YELLOW + "Version"+ ChatColor.GRAY + ": " +ChatColor.DARK_GREEN  + p.getVersion());
			sender.sendMessage(ChatColor.YELLOW + "Made by" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + p.getAuthors());
			sender.sendMessage(ChatColor.YELLOW + "Description" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + p.getDescription());

		}
		else if(cmd.equals("tinkconfig"))
		{
			if(sender.hasPermission("tink.admin.config"))
			{
				if(args.length==2)
				{
					Float floa;
					if(args[0].toLowerCase().equals("sound") || args[0].toLowerCase().equals("s"))
					{
						try {
							@SuppressWarnings("unused")
							Sound sound = Sound.valueOf(args[1].toUpperCase());
							}catch(Exception e) {								
								sender.sendMessage(ChatColor.DARK_RED + "Error" +ChatColor.DARK_GRAY+": " + ChatColor.GRAY+ "Invalid Sound Name.");
								return;
							}							
							plugin.getConfig().set("SoundName", args[1].toUpperCase());
							sender.sendMessage(ChatColor.YELLOW + "Set Value of "+ChatColor.RED+"Sound "+ChatColor.YELLOW+"to " + ChatColor.DARK_GREEN + args[1].toUpperCase());
					}
					
					else if(args[0].toLowerCase().equals("volume") || args[0].toLowerCase().equals("v"))
					{
						
						try {
								floa = Float.parseFloat(args[1]);
							}catch(Exception e) {								
								sender.sendMessage(ChatColor.DARK_RED + "Error" +ChatColor.DARK_GRAY+": " + ChatColor.GRAY+ "Invalid Volume Value (must be a float or int).");
								return;
							}						
							plugin.getConfig().set("Volume", floa);
							sender.sendMessage(ChatColor.YELLOW + "Set Value of "+ChatColor.RED+"Volume "+ChatColor.YELLOW+"to " + ChatColor.DARK_GREEN + args[1]);
						
					}
					else if(args[0].toLowerCase().equals("pitch") || args[0].toLowerCase().equals("p"))
					{
						try {
								floa = Float.parseFloat(args[1]);
							}catch(Exception e) {								
								sender.sendMessage(ChatColor.DARK_RED + "Error" +ChatColor.DARK_GRAY+": " + ChatColor.GRAY+ "Invalid Pitch Value (must be a float or int).");
								return;
							}
							plugin.getConfig().set("Pitch", floa);
							sender.sendMessage(ChatColor.YELLOW + "Set Value of "+ChatColor.RED+"Pitch "+ChatColor.YELLOW+"to " + ChatColor.DARK_GREEN + args[1]);
						
					}
					else if(args[0].toLowerCase().equals("jointoggle") || args[0].toLowerCase().equals("jt"))
					{
						if(args[1].toLowerCase().equals("true") || args[1].toLowerCase().equals("false"))
						{
							plugin.getConfig().set("ToggleonFirstJoin", Boolean.valueOf(args[1]));
							sender.sendMessage(ChatColor.YELLOW + "Set Value of "+ChatColor.RED+"ToggleonFirstJoin "+ChatColor.YELLOW+"to " + ChatColor.DARK_GREEN + args[1]);
							
						}else sender.sendMessage(ChatColor.DARK_RED + "Error" +ChatColor.DARK_GRAY+": " + ChatColor.GRAY+ "Invalid Boolean value (must be true or false).");
					}
					
					plugin.saveConfig();
				}
				else if(args.length>=1 && args[0].toLowerCase().equals("reload"))
				{
					 plugin.reloadConfig();
					 plugin.saveConfig();
			         sender.sendMessage(ChatColor.GREEN + "[Tink] Config reloaded!");
			         System.out.println("[Tink] Config reloaded!");
				}
				else if(args.length>=1 && args[0].toLowerCase().equals("config"))
				{					
			        sender.sendMessage(ChatColor.GREEN+"Sound"+ChatColor.DARK_GRAY+": " +ChatColor.DARK_GREEN+plugin.getConfig().get("SoundName") );
			        sender.sendMessage(ChatColor.GREEN+"Volume"+ChatColor.DARK_GRAY+": " +ChatColor.DARK_GREEN+ plugin.getConfig().get("Volume"));
			        sender.sendMessage(ChatColor.GREEN+"Pitch"+ChatColor.DARK_GRAY+": " +ChatColor.DARK_GREEN+plugin.getConfig().get("Pitch") );
			        sender.sendMessage(ChatColor.GREEN+"ToggleonFirstJoin"+ChatColor.DARK_GRAY+": " +ChatColor.DARK_GREEN+plugin.getConfig().get("ToggleonFirstJoin") );
				}
				
				else sender.sendMessage(ChatColor.RED + "Syntax" + ChatColor.DARK_GRAY + ": " +ChatColor.GRAY+ "/tinkconfig <Sound|Volume|Pitch|JoinToggle|Reload|Config> <value>");
			}
		}


		
	}
}

