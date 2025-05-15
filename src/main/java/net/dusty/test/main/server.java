package net.dusty.test.main;

import net.dusty.test.Testmod;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Testmod.MOD_ID)
public class server {
    private static MinecraftServer instance;

    @SubscribeEvent
    public static void onServerStarted(ServerStartingEvent event) {
        instance = event.getServer();
    }

    public static MinecraftServer getServer() {
        return instance;
    }
}
