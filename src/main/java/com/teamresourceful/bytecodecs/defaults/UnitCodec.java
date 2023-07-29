package com.teamresourceful.bytecodecs.defaults;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public record UnitCodec<T>(Supplier<T> value) implements ByteCodec<T> {

    public UnitCodec(T value) {
        this(() -> value);
    }

    @Override
    public void encode(T value, ByteBuf buffer) {}

    @Override
    public T decode(ByteBuf buffer) {
        return this.value.get();
    }
}
