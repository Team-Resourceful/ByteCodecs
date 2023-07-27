package com.teamresourceful.bytecodecs.defaults;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import io.netty.buffer.ByteBuf;

import java.util.function.BiConsumer;
import java.util.function.Function;

public record PassthroughCodec<T>(BiConsumer<ByteBuf, T> encoder, Function<ByteBuf, T> decoder) implements ByteCodec<T> {

    @Override
    public void encode(T value, ByteBuf buffer) {
        encoder.accept(buffer, value);
    }

    @Override
    public T decode(ByteBuf buffer) {
        return decoder.apply(buffer);
    }
}
