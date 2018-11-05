package com.endercrest.voidspawn.modes;

import com.endercrest.voidspawn.ConfigManager;
import com.endercrest.voidspawn.VoidSpawn;
import com.endercrest.voidspawn.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Command implements SubMode {

    private VoidSpawn plugin;

    public Command(VoidSpawn plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onActivate(Player player, String worldName){
        player.setFallDistance(0);
        String commandString = ConfigManager.getInstance().getString(worldName + ".command", "")
                .replace("${player.name}", player.getName())
                .replace("${player.uuid}", player.getUniqueId().toString())
                .replace("${player.coord.x}", player.getLocation().getBlockX()+"")
                .replace("${player.coord.y}", player.getLocation().getBlockY()+"")
                .replace("${player.coord.z}", player.getLocation().getBlockZ()+"")
                .replace("${player.coord.world}", player.getLocation().getWorld().getName());
        String[] commands = commandString.split(";");
        boolean success = true;
        for(String command : commands){
            boolean status;
            String[] perms = command.split(":", 2);
            //Check if cmd needs to be ran as OP/Console
            if(perms.length > 1 && perms[0].equalsIgnoreCase("op")){
                String cmd = perms[1];
                status = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.trim());
            }else{
                status = player.performCommand(command.trim());
            }

            if(!status){
                plugin.log(String.format("&cCommand Failed for %s! (%s)", worldName, command));
                success = false;
            }
        }
        if(!success){
            player.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + "&cContact Admin. One of the commands failed."));
        }
        return success;
    }

    @Override
    public boolean onSet(String[] args, String worldName, Player p){
        ConfigManager.getInstance().setMode(worldName, args[1]);
        return true;
    }

    @Override
    public Status[] getStatus(String worldName){
        final String command = ConfigManager.getInstance().getString(worldName + ".command", null);
        return new Status[]{
            new Status(command == null ? StatusType.INCOMPLETE : StatusType.COMPLETE,
                String.format("Command Set %s", command == null ? "" : String.format("(%s)", command)))
        };
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public String getHelp(){
        return "&6Command &f- Uses configurable command(s) to send player to spawn";
    }

    @Override
    public String getName(){
        return "Command";
    }
}