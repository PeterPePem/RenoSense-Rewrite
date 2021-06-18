// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.alpha432.oyvey.features.modules.misc.RPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class DiscordPresence
{
    public static DiscordRichPresence presence;
    private static final DiscordRPC rpc;
    private static Thread thread;
    
    public static void start() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordPresence.rpc.Discord_Initialize("842110822221348895", handlers, true, "");
        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.presence.details = ((Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) ? "In the main menu." : ("Playing " + ((Minecraft.getMinecraft().getCurrentServerData() != null) ? (RPC.INSTANCE.showIP.getValue() ? ("on " + Minecraft.getMinecraft().getCurrentServerData().serverIP + ".") : " multiplayer.") : " singleplayer.")));
        DiscordPresence.presence.state = RPC.INSTANCE.state.getValue();
        DiscordPresence.presence.largeImageText = RPC.INSTANCE.largeImageText.getValue();
        DiscordPresence.presence.smallImageKey = "skitttyy";
        DiscordPresence.presence.largeImageKey = "renosense";
        DiscordPresence.presence.smallImageText = RPC.INSTANCE.smallImageText.getValue();
        DiscordPresence.presence.partyId = "ae488379-351d-4a4f-ad32-2b9b01c91657";
        DiscordPresence.presence.partyMax = 50;
        DiscordPresence.presence.partySize = 1;
        DiscordPresence.presence.spectateSecret = "c2puZXpzcGVjdDR0ZWxvbA==";
        DiscordPresence.presence.joinSecret = "cmVub1MzbnNlam9pbg==";
        DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
        (DiscordPresence.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordPresence.rpc.Discord_RunCallbacks();
                String string = "";
                StringBuilder sb = new StringBuilder();
                DiscordRichPresence presence;
                presence = DiscordPresence.presence;
                new StringBuilder().append("Playing ");
                if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                    if (RPC.INSTANCE.showIP.getValue()) {
                        string = "on " + Minecraft.getMinecraft().getCurrentServerData().serverIP + ".";
                    }
                    else {
                        string = " multiplayer.";
                    }
                }
                else {
                    string = " not multiplayer.";
                }
                presence.details = sb.append(string).toString();
                DiscordPresence.presence.state = RPC.INSTANCE.state.getValue();
                DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler")).start();
    }
    
    public static void stop() {
        if (DiscordPresence.thread != null && !DiscordPresence.thread.isInterrupted()) {
            DiscordPresence.thread.interrupt();
        }
        DiscordPresence.rpc.Discord_Shutdown();
    }

    public static void accept( ) {

    }

    static {
        rpc = DiscordRPC.INSTANCE;
        DiscordPresence.presence = new DiscordRichPresence();
    }
}
