package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

public class PatternTest extends CommandExampleBase {
    private final static String patternA = "!maizel";
    private final static String patternB = "!roncheer";
    private int Mimp = 0;
    private int Cheers = 0;

    private final Twirk twirk;

    public PatternTest(Twirk twirk) {
        super(CommandType.PREFIX_COMMAND);
        this.twirk = twirk;
    }

    @Override
    protected String getCommandWords() {
        return patternA + "|" + patternB;
    }

    @Override
    protected USER_TYPE getMinUserPrevilidge() {
        return USER_TYPE.DEFAULT;
    }

    @Override
    protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
        if( command.equals(patternA) ) {
            Mimp++;
            twirk.channelMessage("Maize has sworn " + Mimp + " times. Maize L");
        }
        else if( command.equals(patternB) ) {
            Cheers++;
            twirk.channelMessage("@ronaldotheturtl Cheeres Buddy: " + Cheers);
            //twirk.channelMessage(sender.getDisplayName() + ": Local time is " + new Date().toString());
        }

    }

}
