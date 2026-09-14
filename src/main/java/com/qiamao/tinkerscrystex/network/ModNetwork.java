package com.qiamao.tinkerscrystex.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetwork {
    public static SimpleNetworkWrapper INSTANCE;

    public static void init() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("tinkerscrystex");

        // 注册数据包 (包类, 处理类, 包ID, 接收端)
        // 霜冻状态包从服务端发往客户端，包 ID 为 0
        INSTANCE.registerMessage(MessageFrostSyncHandler.class, MessageFrostSync.class, 0, Side.CLIENT);
    }
}