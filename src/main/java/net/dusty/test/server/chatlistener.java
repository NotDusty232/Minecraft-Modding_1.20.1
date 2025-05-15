package net.dusty.test.server;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.dusty.test.fakeplayer.behaviors;

@Mod.EventBusSubscriber
public class chatlistener {
    @SubscribeEvent
    public static void onChat(final ServerChatEvent event) {
        String message = event.getMessage().getString();
        var player = event.getPlayer();

        if (message.equalsIgnoreCase("hello?")) {
            behaviors.spawn(player);
        }
    }
}
