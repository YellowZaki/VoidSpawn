package com.endercrest.voidspawn.modes;

import org.bukkit.entity.Player;

import java.util.List;

public interface SubMode {

    /**
     * Called when the player enters the void.
     *
     * @param player    The player who has entered the void and be teleported or whatever the action is.
     * @param worldName The world name in which the player resides.
     * @return Returns whether the action successfully occurred.
     */
    boolean onActivate(Player player, String worldName);

    /**
     * This method is called when this mode is set in a world. This is meant to give any additional instructions should a mode require it.
     * As well as change any settings if necessary.
     *
     * @param args      The args from the command on the set.
     * @param worldName The world name where the mode was set.
     * @param p         The player who invoked the command to set the mode.
     * @return Returns whether the onSet method successfully executed and gave proper details.
     */
    boolean onSet(String[] args, String worldName, Player p);

    /**
     *
     * @param worldName
     * @return
     */
    Status[] getStatus(String worldName);

    boolean isEnabled();

    /**
     * Add deals about the mode and is displayed when the player asks for the list of mods.
     * ex. "&6Spawn &f- Will teleport player to set spot."
     *
     * @return Returns the string that will be displayed upon player request details of the mode.
     */
    String getHelp();

    /**
     * Get the name of the mode.
     * @return The string version of the mode.
     */
    String getName();

    enum StatusType {
        COMPLETE,
        INCOMPLETE,
        INFO,
    }

    class Status {
        private StatusType type;
        private String message;

        public Status(StatusType type, String message) {
            this.type = type;
            this.message = message;
        }

        public StatusType getType(){
            return type;
        }

        public String getMessage(){
            return message;
        }
    }
}