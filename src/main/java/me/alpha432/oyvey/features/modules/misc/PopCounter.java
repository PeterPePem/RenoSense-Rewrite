package me.alpha432.oyvey.features.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.client.HUD;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public class PopCounter
        extends Module {
    public static HashMap<String, Integer> TotemPopContainer = new HashMap();
    private static PopCounter INSTANCE = new PopCounter();

    public PopCounter() {
        super("PopCounter", "Counts other players totem pops.", Module.Category.MISC, true, true, false);
        this.setInstance();
    }

    public static PopCounter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PopCounter();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        TotemPopContainer.clear();
    }

    public void onDeath(EntityPlayer player) {
        if (TotemPopContainer.containsKey(player.getName())) {
            int l_Count = TotemPopContainer.get(player.getName());
            TotemPopContainer.remove(player.getName());
            if(HUD.getInstance().timestamp.getValue()) {
                if (l_Count == 1) {
                    Command.sendMessage(getTimeString() + " " + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.WHITE + " Totem!");
                } else {
                    Command.sendMessage(getTimeString() + " " + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.WHITE + " Totems!");
                }
            } else if(!HUD.getInstance().timestamp.getValue()) {
                if (l_Count == 1) {
                    Command.sendMessage(ChatFormatting.WHITE + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + " Totem!");
                } else {
                    Command.sendMessage(ChatFormatting.WHITE + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + " Totems!");
                }
            }
        }
        return;
    }

    public void onTotemPop(EntityPlayer player) {
        if (PopCounter.fullNullCheck()) {
            return;
        }
        if (PopCounter.mc.player.equals(player)) {
            return;
        }
        int l_Count = 1;
        if (TotemPopContainer.containsKey(player.getName())) {
            l_Count = TotemPopContainer.get(player.getName());
            TotemPopContainer.put(player.getName(), ++l_Count);
        } else {
            TotemPopContainer.put(player.getName(), l_Count);
        }
        if(HUD.getInstance().timestamp.getValue()) {
            if (l_Count == 1) {
                Command.sendMessage(getTimeString() + " " + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.WHITE + " Totem.");
            } else {
                Command.sendMessage(getTimeString() + " " + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.WHITE + " Totems.");
            }
        } else if(!HUD.getInstance().timestamp.getValue()) {
            if (l_Count == 1) {
                Command.sendMessage(ChatFormatting.WHITE + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.WHITE + " Totem.");
            } else {
                Command.sendMessage(ChatFormatting.WHITE + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.WHITE + " Totems.");
            }
        }
    }

}