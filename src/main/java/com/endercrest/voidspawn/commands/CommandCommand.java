package com.endercrest.voidspawn.commands;

import com.endercrest.voidspawn.ConfigManager;
import com.endercrest.voidspawn.VoidSpawn;
import com.endercrest.voidspawn.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandCommand implements SubCommand {

    @Override
    public boolean onCommand(Player p, String[] args){
        if(args.length > 1){
            String command = "";
            for(int i = 1; i < args.length; i++){
                command += args[i] + " ";
            }
            ConfigManager.getInstance().setCommand(command, p.getWorld().getName());
        }else{
            ConfigManager.getInstance().setCommand(null, p.getWorld().getName());
            p.sendMessage(MessageUtil.colorize("Removed Command(s)"));
            return true;
        }
        p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + "Command(s) Set"));
        return true;
    }

    @Override
    public String helpInfo(){
        return "/vs command [commands] - Set command(s) for the command mode, separate commands with semicolon.";
    }

    @Override
    public String permission(){
        return "vs.admin.command";
    }

    @Override
    public List<String> getTabCompletion(Player player, String[] args) {
        // TODO Make this smart
        // Could try to call other command tab completions.
        return new ArrayList<>();
    }
}