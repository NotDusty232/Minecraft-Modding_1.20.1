package net.dusty.test.fakeplayer;

import net.dusty.test.fakeplayer.entity;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.core.jmx.Server;

public class behaviors {
    public static void spawn(ServerPlayer player) {
        entity.spawn(player);
    }
}
