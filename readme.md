### ByteCodecs

An easy to use defined netty bytebuf reader and writer inspired from Mojang's DataFixerUpper serialization Codecs.


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
