package com.teamresourceful.bytecodecs.defaults;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public record MapDispatchCodec<K, V>(
        ByteCodec<K> keyCodec, Function<K, ByteCodec<V>> getter
) implements ByteCodec<Map<K, V>> {

    @Override
    public void encode(Map<K, V> value, ByteBuf buffer) {
        ByteBufUtils.writeVarInt(buffer, value.size());
        value.forEach((key, v) -> {
            keyCodec.encode(key, buffer);
            ByteCodec<V> codec = getter.apply(key);
            codec.encode(v, buffer);
        });
    }

    @Override
    public Map<K, V> decode(ByteBuf buffer) {
        int size = ByteBufUtils.readVarInt(buffer);
        Map<K, V> map = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            K key = keyCodec.decode(buffer);
            ByteCodec<V> codec = getter.apply(key);
            map.put(key, codec.decode(buffer));
        }
        return map;
    }
}
