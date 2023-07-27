package com.teamresourceful.bytecodecs.defaults;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public record ListCodec<T>(ByteCodec<T> codec) implements ByteCodec<List<T>> {

    @Override
    public void encode(List<T> value, ByteBuf buffer) {
        ByteBufUtils.writeVarInt(value.size(), buffer);
        for (T t : value) {
            codec.encode(t, buffer);
        }
    }

    @Override
    public List<T> decode(ByteBuf buffer) {
        int size = ByteBufUtils.readVarInt(buffer);
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(codec.decode(buffer));
        }
        return list;
    }
}
