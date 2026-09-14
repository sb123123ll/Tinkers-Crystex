package com.qiamao.tinkerscrystex.network;

import com.qiamao.tinkerscrystex.client.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageFrostSyncHandler implements IMessageHandler<MessageFrostSync, IMessage> {
    @Override
    public IMessage onMessage(MessageFrostSync message, MessageContext ctx) {
        // 在客户端主线程上执行
        Minecraft.getMinecraft().addScheduledTask(() -> {
            Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityId);
            if (entity != null) {
                // 将接收到的冰冻状态存储在客户端端
                ClientEventHandler.setEntityFrosted(entity.getEntityId(), message.isFrosted);
            }
        });
        return null;
    }
}