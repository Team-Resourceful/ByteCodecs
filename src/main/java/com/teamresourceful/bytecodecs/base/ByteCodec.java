package com.teamresourceful.bytecodecs.base;

import com.teamresourceful.bytecodecs.defaults.*;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ByteCodec<T> {

    void encode(T value, ByteBuf buffer);

    T decode(ByteBuf buffer);

    default ByteCodec<List<T>> listOf() {
        return new ListCodec<>(this);
    }

    default ByteCodec<Optional<T>> optional() {
        return new OptionalCodec<>(this, null);
    }

    default ByteCodec<Optional<T>> optional(T value) {
        return new OptionalCodec<>(this, value);
    }

    default ByteCodec<Optional<T>> optional(Supplier<T> value) {
        return new OptionalSupplierCodec<>(this, value);
    }

    default <O> ObjectEntryByteCodec<O, T> fieldOf(Function<O, T> getter) {
        return new ObjectEntryByteCodec<>(this, getter);
    }

    default <R> ByteCodec<R> map(Function<T, R> decoder, Function<R, T> encoder) {
        return new MappingCodec<>(this, decoder, encoder);
    }

    ByteCodec<String> STRING = StringCodec.INSTANCE;
    ByteCodec<Boolean> BOOLEAN = new PassthroughCodec<>(ByteBuf::writeBoolean, ByteBuf::readBoolean);
    ByteCodec<Byte> BYTE = new PassthroughCodec<>((buf, value) -> buf.writeByte(value), ByteBuf::readByte);
    ByteCodec<Short> SHORT = new PassthroughCodec<>((buf, value) -> buf.writeShort(value), ByteBuf::readShort);
    ByteCodec<Integer> INT = new PassthroughCodec<>(ByteBuf::writeInt, ByteBuf::readInt);
    ByteCodec<Long> LONG = new PassthroughCodec<>(ByteBuf::writeLong, ByteBuf::readLong);
    ByteCodec<Float> FLOAT = new PassthroughCodec<>(ByteBuf::writeFloat, ByteBuf::readFloat);
    ByteCodec<Double> DOUBLE = new PassthroughCodec<>(ByteBuf::writeDouble, ByteBuf::readDouble);

}
