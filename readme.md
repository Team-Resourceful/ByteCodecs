### ByteCodecs

This is an experimental project which allows for the easy encoding and decoding of Netty ByteBufs


#### Example
```java
    public record ExampleCodecObject(int anInt, String aString, boolean aBoolean) {
        
        public static final ByteCodec<String> STRING_NO_SPACES = ByteCodec.STRING.map(String::strip, Function.identity());
        
        public static final ByteCodec<ExampleCodecObject> CODEC = ObjectByteCodec.create(
                ByteCodec.INT.fieldOf(ExampleCodecObject::anInt),
                STRING_NO_SPACES.fieldOf(ExampleCodecObject::aString),
                ByteCodec.BOOLEAN.fieldOf(ExampleCodecObject::aBoolean),
                ExampleCodecObject::new
        );
        
    }
```
