package net.dusty.test.fakeplayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber()
public class entity {
    private static boolean spawned = false;
    private static ServerPlayer fakePlayer;
    private static ServerPlayer targetPlayer;

    public static void spawn(ServerPlayer event) {
        if (spawned) return;
        spawned = true;

        MinecraftServer server = net.dusty.test.main.server.getServer();
        ServerLevel world = server.getLevel(ServerLevel.OVERWORLD);
        ServerPlayer joiningPlayer = (ServerPlayer) event;

        GameProfile profile = new GameProfile(UUID.randomUUID(), "Shadorot");

        network fakeConnection = new network();
        fakePlayer = new ServerPlayer(server, world, profile);
        ServerGamePacketListenerImpl listener = new ServerGamePacketListenerImpl(server, fakeConnection, fakePlayer);
        fakePlayer.connection = listener;

        fakePlayer.setGameMode(GameType.SURVIVAL);
        fakePlayer.setNoGravity(false);
        fakePlayer.teleportTo(world,
                joiningPlayer.getX(),
                joiningPlayer.getY(),
                joiningPlayer.getZ(),
                joiningPlayer.getYRot(),
                joiningPlayer.getXRot());

        server.getPlayerList().placeNewPlayer(fakeConnection, fakePlayer);

        targetPlayer = event;

        System.out.println("Fake player spawned as: " + fakePlayer.getName().getString());
    }


    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (fakePlayer == null || targetPlayer == null) return;

        double dx = targetPlayer.getX() - fakePlayer.getX();
        double dy = targetPlayer.getEyeY() - fakePlayer.getEyeY();
        double dz = targetPlayer.getZ() - fakePlayer.getZ();

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        double yaw = Math.toDegrees(Math.atan2(dz, dx)) - 90;
        double pitch = Math.toDegrees(-Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

        fakePlayer.setYRot((float) yaw);
        fakePlayer.setXRot((float) pitch);
        fakePlayer.yHeadRot = (float) yaw;
        fakePlayer.yBodyRot = (float) yaw;

        if (distance > 2.0) {
            double speed = 0.25;
            double vx = (dx / distance) * speed;
            double vz = (dz / distance) * speed;
            double vy = fakePlayer.getDeltaMovement().y;

            fakePlayer.setDeltaMovement(vx, fakePlayer.getDeltaMovement().y, vz);
            fakePlayer.moveTo(
                    fakePlayer.getX() + vx,
                    fakePlayer.getY(),
                    fakePlayer.getZ() + vz
            );
        } else {
            fakePlayer.setDeltaMovement(0, fakePlayer.getDeltaMovement().y, 0);
        }
    }
}
