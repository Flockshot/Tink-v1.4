package me.Flockshot.Tink;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin
{

	
	public void onEnable()
	{		
		
		PluginDescriptionFile plug = getDescription();
		Logger logger = getLogger();
		//getServer().getPluginManager().registerEvents(new ChatPacket(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		new Commands(this);
		


		logger.info( plug.getName() + " " + plug.getVersion() + " has been loaded.");		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		
		File f = new File(this.getDataFolder(), "TinkToggle.yml");

		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
		Sound s = Sound.valueOf((String) this.getConfig().get("SoundName"));
		
		if(s==null)
		{
			logger.info( plug.getName() + " " + plug.getVersion() + " Invalid Sound Name in Config.");	
			this.getPluginLoader().disablePlugin(this);
		}

		
    }
		
		
		
		
	
	
	
	public void onDisable()
	{		
		PluginDescriptionFile plug = getDescription();
		Logger logger = getLogger();
		logger.info( plug.getName() + " " + plug.getVersion() + " has been disabled.");
		
	}
	

	

	
	
	public boolean onCommand(CommandSender sender, Command command, String cmdvalue, String[] args)
	{
		String cmd = cmdvalue.toLowerCase();
		
		try {
			Commands.ProcessCommand(sender, command, cmd, args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	

}
