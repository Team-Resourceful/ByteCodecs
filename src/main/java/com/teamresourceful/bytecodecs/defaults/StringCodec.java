package com.teamresourceful.bytecodecs.defaults;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public final class StringCodec implements ByteCodec<String> {

    public static final StringCodec INSTANCE = new StringCodec();

    @Override
    public void encode(String value, ByteBuf buffer) {
        if (value.length() > 32767) {
            throw new RuntimeException("String too big (was " + value.length() + " characters, max " + 32767 + ")");
        } else {
            byte[] bs = value.getBytes(StandardCharsets.UTF_8);
            if (bs.length > 98301) {
                throw new RuntimeException("String too big (was " + bs.length + " bytes encoded, max " + 98301 + ")");
            } else {
                ByteBufUtils.writeVarInt(bs.length, buffer);
                buffer.writeBytes(bs);
            }
        }
    }

    @Override
    public String decode(ByteBuf buffer) {
        int length = ByteBufUtils.readVarInt(buffer);
        if (length > 98301) {
            throw new RuntimeException("The received encoded string buffer length is longer than maximum allowed (" + length + " > " + 98301 + ")");
        } else if (length < 0) {
            throw new RuntimeException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            String string = buffer.toString(buffer.readerIndex(), length, StandardCharsets.UTF_8);
            buffer.readerIndex(buffer.readerIndex() + length);
            if (string.length() > 32767) {
                throw new RuntimeException("The received string length is longer than maximum allowed (" + string.length() + " > " + 32767 + ")");
            }
            return string;
        }
    }
}
