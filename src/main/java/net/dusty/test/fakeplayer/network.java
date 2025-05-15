package net.dusty.test.fakeplayer;

import io.netty.channel.embedded.EmbeddedChannel;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class network extends Connection {
    // Idea from https://github.com/tanyaofei/minecraft-fakeplayer/blob/master/fakeplayer-v1_20_1/src/main/java/io/github/hello09x/fakeplayer/v1_20_1/network/FakeConnection.java
    public network() {
        super(PacketFlow.SERVERBOUND);
        try {
            Field channelField = Connection.class.getDeclaredField("channel");
            channelField.setAccessible(true);
            channelField.set(this, new EmbeddedChannel());

            Field addressField = Connection.class.getDeclaredField("address");
            addressField.setAccessible(true);
            addressField.set(this, new InetSocketAddress("127.0.0.1", 0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
