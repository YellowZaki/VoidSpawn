package com.endercrest.voidspawn.commands;

import com.endercrest.voidspawn.ModeManager;
import com.endercrest.voidspawn.VoidSpawn;
import com.endercrest.voidspawn.modes.SubMode;
import com.endercrest.voidspawn.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ModesCommand implements SubCommand {

    @Override
    public boolean onCommand(Player p, String[] args){
        if(!p.hasPermission(permission())){
            p.sendMessage(MessageUtil.colorize("&cYou do not have permission."));
            return true;
        }
        p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + "--- &6Available Modes&f ---"));
        for(String s : ModeManager.getInstance().getModes().keySet()){
            SubMode mode = ModeManager.getInstance().getSubMode(s);
            if(!mode.isEnabled())
                continue;
            p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + mode.getHelp()));
        }
        return true;
    }

    @Override
    public String helpInfo(){
        return "/vs modes - Gets all available modes";
    }

    @Override
    public String permission(){
        return "vs.admin.modes";
    }

    @Override
    public List<String> getTabCompletion(Player player, String[] args) {
        return new ArrayList<>();
    }
}