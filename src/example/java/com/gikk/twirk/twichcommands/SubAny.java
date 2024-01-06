package com.gikk.twirk.twichcommands;

import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.usernotice.Usernotice;
import com.gikk.twirk.types.users.TwitchUser;

import java.util.*;

public abstract class SubAny implements TwirkListener {
    /**
     * A PREFIX_COMMAND are the classical commands that starts with a certain pattern (e.g. !time)
     * A CONTENT_COMMAND is a command that looks for a certain pattern in the message (e.g. this is !tick)
     */
    public enum CommandType{ TAG_COMMAND }

    //***********************************************************************************************
    //											VARIABLES
    //***********************************************************************************************
    private Set<String> commandPattern;
    private CommandType type;
    private USER_TYPE minPrivilidge;

    //***********************************************************************************************
    //											CONSTRUCTOR
    //***********************************************************************************************
    /**Base class for simpler chat commands. Simple chat commands perform a certain
     * action whenever a certain pattern of characters are seen.
     *
     * @param type What type of command is this. PREFIX_COMMAND or CONTENT_COMMAND
     */
    protected SubAny(CommandType type){
        this.type = type;
        commandPattern = compile();
        minPrivilidge  = getMinUserPrevilidge();
    }

    //***********************************************************************************************
    //											PUBLIC
    //***********************************************************************************************
    @Override
    public void onUsernotice(TwitchUser sender, Usernotice usernotice) {
        /* This could've been done with REGEX matching, instead of using startsWith()/contains().
         *
         * This is much simpler though and easier to understand and maintain. Also, since the
         * amount of work required is so small, the gain from using a Matcher is probably
         * close to zero
         *
         * We get the command by stripping everything but the first word away.
         * This is used when looking for prefix commands
         */
        String content = usernotice.getMessageID();
        Boolean Issub = usernotice.isSubscription();
//        System.out.println("Figuring out message types");
//        System.out.println(usernotice.getSystemMessage());
//        System.out.println(usernotice.getMessage());
//        System.out.println(usernotice.getRaw());
//        System.out.println(content);
//        System.out.println(Issub);
//        System.out.println("End of Message types");

         if (sender.getUserType().value >= minPrivilidge.value) {
//            System.out.println("Stage 1: Privelege Hit");
            if (type == CommandType.TAG_COMMAND) {
//                System.out.println("Stage 2: Passed Tag Command Filter");
                    if(Issub == true )
                    {
//                        System.out.println("Stage 3: Preforming Count");
                        performCommand(sender, usernotice);
                    }
                }
            }
        }

    //***********************************************************************************************
    //											ABSTRACT
    //***********************************************************************************************
    /**This method must return the words this command should react to. If the command
     * listen to several words, they should be separated by {@code |} signs. <br><br>
     * <b>Examlpe:</b> {@code !stats|!stat|?stats|?stat} will react to those chat lines
     * that starts with any of those 4 strings
     * <br><br>
     * Be aware that the pattern recognizer will be case in-sensitive
     *
     * @return A string, comprising of all words this command reacts to
     */
    protected abstract String getCommandWords();

    /**This method must return the minimum {@link USER_TYPE} required to invoke this command.<br>
     * For example, if only USER_TYPE.MOD or higher should be able to invoke the command, this
     * method should return USER_TYPE.MOD. <br><br>
     *
     * If everyone should be able to invoke it, simply return {@link USER_TYPE#DEFAULT}
     *
     * @return The minimum {@link USER_TYPE} which can invoke this command.
     */
    protected abstract USER_TYPE getMinUserPrevilidge();

    /** This method is the commands execution. This will be called whenever a chat line
     * is seen that matches the commandPattern
     *
     * @param command The string that caused us to fire this command
     * @param sender The IrcUser who issued the command
     * @param message The IrcMessage that triggered the command
     */
    //protected abstract void performCommand(String command, TwitchUser sender, TwitchMessage message);
    protected abstract void performCommand(TwitchUser sender, Usernotice usernotice);



    //***********************************************************************************************
    //											PRIVATE
    //***********************************************************************************************
    private Set<String> compile(){
        String[] patterns = getCommandWords().toLowerCase(Locale.ENGLISH).split("\\|");
        HashSet<String> out = new HashSet<>();
        Collections.addAll(out, patterns);
        return out;
    }
}


