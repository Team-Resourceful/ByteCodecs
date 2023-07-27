package com.teamresourceful.bytecodecs.utils;

import io.netty.buffer.ByteBuf;

public final class ByteBufUtils {

    public static void writeVarInt(int input, ByteBuf buffer) {
        while((input & -128) != 0) {
            buffer.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        buffer.writeByte(input);
    }

    public static int readVarInt(ByteBuf buffer) {
        int i = 0;
        int j = 0;

        byte b;
        do {
            b = buffer.readByte();
            i |= (b & 127) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while((b & 128) == 128);

        return i;
    }
}
