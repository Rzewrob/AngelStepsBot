package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

public class Counts extends CommandExampleBase {
	private final static String patternA = "!subcount";
	private final static String patternB = "!bitcount";
//	private final static String patternC = "!zerobitcount";
//	private final static String patternD = "!SetBits";
	
	private final Twirk twirk;
	
	public Counts(Twirk twirk) {
		super(CommandType.PREFIX_COMMAND);
		this.twirk = twirk;
	}

	@Override
	protected String getCommandWords()  {
		return patternA + "|" + patternB;
	}

	@Override
	protected USER_TYPE getMinUserPrevilidge() {
		return USER_TYPE.MOD;
	}

	@Override
	protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
		if( command.equals(patternA) ) {
			twirk.channelMessage("Current Subs: " + twirk.getSubcount() + " for a value of " + String.format("%.2f",twirk.getSubValue()));
			twirk.channelMessage("Tier1Subs: " + twirk.getTier1() + " | Tier2Subs: " + twirk.getTier2() + " | Tier3Subs: " + twirk.getTier3());
//			System.out.println("testA");
		}
		else if( command.equals(patternB) ) {
			twirk.channelMessage("Current Bits: " + twirk.getCheerCount() + " for a value of " + String.format("%.2f",twirk.getCheervalue()));
//			System.out.println("testB");
		}
//		else if (command.equals(patternC)){
//			twirk.setCheerCount(0);
//			twirk.setCheervalue(0);
//			twirk.channelMessage("Current Bits: " + twirk.getCheerCount() + " for a value of " + String.format("%.2f",twirk.getCheervalue()));
//		}
//		else if (command.equals(patternD)){
//			String test = message.getContent();
//			System.out.println();
//			twirk.setCheervalue(1);
//			String value = String.valueOf(twirk.getCheervalue());
//			twirk.channelMessage(value);
//		}
			
	}	
	
}
