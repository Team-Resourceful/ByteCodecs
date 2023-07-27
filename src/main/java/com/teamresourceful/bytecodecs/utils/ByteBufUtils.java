package com.teamresourceful.bytecodecs.utils;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public final class ByteBufUtils {

    public static void writeVarInt(ByteBuf buffer, int input) {
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

    public static void writeUUID(ByteBuf buffer, UUID uuid) {
        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
    }

    public static UUID readUUID(ByteBuf buffer) {
        return new UUID(buffer.readLong(), buffer.readLong());
    }
}
