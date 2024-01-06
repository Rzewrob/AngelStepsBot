package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.usernotice.Usernotice;
import com.gikk.twirk.types.users.TwitchUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubPattern extends SubAny {

    private static String PATTERNA = "subgift";
    private static String PATTERNB = "resub";
    private static String PATTERNC = "submysterygift";
    public int SubCount = 0;
    public double SubValue = 0;
    public int Tier1 = 0;
    public int Tier2 = 0;
    public int Tier3 = 0;

    private final Twirk twirk;

    public SubPattern(Twirk twirk) {
        super(CommandType.TAG_COMMAND);
        this.twirk = twirk;
    }

    @Override
    protected String getCommandWords() {
        return PATTERNA + "|" + PATTERNB;
    }

    @Override
    protected USER_TYPE getMinUserPrevilidge() {
        return USER_TYPE.DEFAULT;
    }

     @Override
    protected void performCommand(TwitchUser sender, Usernotice usernotice) {
        System.out.println("Recongized a Sub from " + sender.getUserName() + " for " + usernotice.getRaw());
        int ChValue = 0;
        int tiervalue =0;
        String TMessage = usernotice.getRaw();
        System.out.println("Tag = " + TMessage);
        Pattern p = Pattern.compile("msg-param-sub-plan=(\\d*);");
        Pattern Prime = Pattern.compile("msg-param-sub-plan=(.*?);");
        Pattern Tier = Pattern.compile("sTier\\"+"(s\\d*\\)");
        Matcher m = p.matcher(TMessage);
        Matcher PrimeMatcher  = Prime.matcher(TMessage);
        Matcher m2 = Tier.matcher(TMessage);
        if (PrimeMatcher.find())
        {
            if (PrimeMatcher.group(1).equals("Prime"))
            {
                ChValue = 1;
                //System.out.println("Hit Prime Check");
            }
        }
        if(m.find())
        {
            ChValue = Integer.parseInt(m.group(1));
            ChValue /= 1000;
        }
//        if(tiervalue == 3)
//        {
//            tiervalue *= 5;
//        }

//         fileWriter.writeLineToFile("New Sub: " + ChValue, null);

        if(ChValue == 1)
        {
            SubCount += ChValue;
            Tier1++;
            System.out.println("Tier 1 Count: " + Tier1);
            twirk.setTier1(Tier1);
            twirk.setSubcount(SubCount);
            // twirk.channelMessage("Count: " + count);
        }
        else if (ChValue == 2)
        {
            SubCount += ChValue;
            Tier2++;
            System.out.println("Tier 2 Count: " + Tier2);
            twirk.setTier2(Tier2);
            twirk.setSubcount(SubCount);
            // twirk.channelMessage("Count: " + count);

        }
        else if ( ChValue == 3)
        {
            SubCount += 5;
            Tier3++;
            System.out.println("Tier 3 Count: " + Tier3);
            twirk.setTier3(Tier3);
            twirk.setSubcount(SubCount);
            // twirk.channelMessage("Count: " + count);

        }
        else
        {
            System.out.println("*****Hmm not recongized tier size debug this?*****");
        }


    }
}

//normal message
//@badge-info=;badges=broadcaster/1,premium/1;client-nonce=cd024a4840b17637ce39c64f8ea49242;color=#FF0000;display-name=Pkrock;emotes=;first-msg=0;flags=;id=d09f441d-2676-486b-af3d-3ab2ff98ee55;mod=0;returning-chatter=0;
// room-id=41911856;subscriber=0;tmi-sent-ts=1690442388991;turbo=0;user-id=41911856;user-type= :pkrock!pkrock@pkrock.tmi.twitch.tv PRIVMSG #pkrock :test

//cheer example
// @badge-info=;badges=glhf-pledge/1;bits=1;color=#0000FF;display-name=WhosMontu;emotes=;first-msg=1;flags=;id=998b364d-b7ed-4b82-9d9e-a7046122b9f0;mod=0;returning-chatter=0;room-id=641972806;
// subscriber=0;tmi-sent-ts=1690439036917;turbo=0;user-id=464801243;user-type= :whosmontu!whosmontu@whosmontu.tmi.twitch.tv PRIVMSG #kaicenat :Cheer1

//@badge-info=subscriber/11;badges=subscriber/9,no_video/1;color=#CC00BE;display-name=Necrothunder;emotes=emotesv2_fac9579a586f4b36a7c21e383be42e81:0-11/emotesv2_ab9b81ab9e604163b479752364d0bf84:13-24;flags=;
// id=444f92fd-d271-471d-8fbc-8007da894e8e;login=necrothunder;mod=0;msg-id=resub;msg-param-cumulative-months=11;msg-param-months=0;msg-param-multimonth-duration=0;msg-param-multimonth-tenure=0;msg-param-should-share-streak=1;
// msg-param-streak-months=11;msg-param-sub-plan-name=Channel\sSubscription\s(fefe_asmr);msg-param-sub-plan=1000;msg-param-was-gifted=false;room-id=806193178;subscriber=1;
// system-msg=Necrothunder\ssubscribed\sat\sTier\s1.\sThey've\ssubscribed\sfor\s11\smonths,\scurrently\son\sa\s11\smonth\sstreak!;tmi-sent-ts=1690439510004;user-id=23749073;user-type= :tmi.twitch.tv USERNOTICE #fefe_asmr :fefeasmrCozy fefeasmrMelt

//gift sub
//IN  @badge-info=;badges=premium/1;color=#FF4500;display-name=LymbasLuchs;emotes=;flags=;id=9ad3a050-1534-44ad-b313-4a3db2c8a63b;login=lymbasluchs;mod=0;msg-id=subgift;msg-param-gift-months=1;msg-param-months=1;
// msg-param-origin-id=c9\s46\sa6\sb9\s63\s2a\s89\sec\s2f\se6\seb\s54\sb2\s4a\sd2\sc0\scc\s21\s49\s34;msg-param-recipient-display-name=Nanimonai_Ch;msg-param-recipient-id=44894455;msg-param-recipient-user-name=nanimonai_ch;
// msg-param-sender-count=0;msg-param-sub-plan-name=eee;msg-param-sub-plan=1000;room-id=449858930;subscriber=0;system-msg=LymbasLuchs\sgifted\sa\sTier\s1\ssub\sto\sNanimonai_Ch!;tmi-sent-ts=1690477316492;user-id=82325722;user-type= :tmi.twitch.tv USERNOTICE #meowmoonified

//IN  @badge-info=subscriber/12;badges=moderator/1,subscriber/3012,bits/75000;color=#FF0000;display-name=Pkrock;emotes=;flags=;id=2d998d48-9ced-4d59-a786-b9bf27bf07f4;login=pkrock;mod=1;msg-id=submysterygift;
// msg-param-goal-contribution-type=SUBS;msg-param-goal-current-contributions=509;msg-param-goal-description=RENTTTTTT;msg-param-goal-target-contributions=600;msg-param-goal-user-contributions=1;msg-param-mass-gift-count=1;
// msg-param-origin-id=97\s4c\s70\sb1\sc1\s8c\s63\sd7\s3b\s02\sf1\s90\s07\s8d\s96\sb9\s0f\sec\seb\sd0;msg-param-sender-count=929;msg-param-sub-plan=1000;room-id=550565368;
// subscriber=1;system-msg=Pkrock\sis\sgifting\s1\sTier\s1\sSubs\sto\sAngel_Steps's\scommunity!\sThey've\sgifted\sa\stotal\sof\s929\sin\sthe\schannel!;tmi-sent-ts=1690490171524;user-id=41911856;user-type=mod :tmi.twitch.tv USERNOTICE #angel_steps
