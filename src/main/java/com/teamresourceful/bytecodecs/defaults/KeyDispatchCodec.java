package com.teamresourceful.bytecodecs.defaults;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import io.netty.buffer.ByteBuf;

import java.util.function.Function;

public record KeyDispatchCodec<K, V>(
        ByteCodec<K> keyCodec, Function<K, ByteCodec<V>> getter, Function<V, K> keyGetter
) implements ByteCodec<V> {

    @Override
    public void encode(V value, ByteBuf buffer) {
        K key = keyGetter.apply(value);
        keyCodec.encode(key, buffer);
        ByteCodec<V> codec = getter.apply(key);
        codec.encode(value, buffer);
    }

    @Override
    public V decode(ByteBuf buffer) {
        K key = keyCodec.decode(buffer);
        ByteCodec<V> codec = getter.apply(key);
        return codec.decode(buffer);
    }
}
