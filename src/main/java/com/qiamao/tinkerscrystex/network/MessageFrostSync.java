package com.qiamao.tinkerscrystex.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageFrostSync implements IMessage {
    public int entityId;
    public boolean isFrosted;

    // 必须保留的无参构造函数
    public MessageFrostSync() {}

    public MessageFrostSync(int entityId, boolean isFrosted) {
        this.entityId = entityId;
        this.isFrosted = isFrosted;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.isFrosted = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.isFrosted);
    }
}