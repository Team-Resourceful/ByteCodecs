import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.defaults.KeyDispatchCodec;
import com.teamresourceful.bytecodecs.defaults.MapDispatchCodec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class DispatchTests extends TestBase {

    @Test
    public void mapDispatch() {
        ByteCodec<Number> intCodec = ByteCodec.INT.map(i -> i, Number::intValue);
        ByteCodec<Number> doubleCodec = ByteCodec.DOUBLE.map(d -> d, Number::doubleValue);

        MapDispatchCodec<Byte, Number> codec = new MapDispatchCodec<>(ByteCodec.BYTE, key -> {
            if (key == 0x01) {
                return intCodec;
            } else if (key == 0x02) {
                return doubleCodec;
            }
            throw new IllegalArgumentException("Unknown key: " + key);
        });

        Map<Byte, Number> map = Map.of(
                (byte) 0x01, 0x1234,
                (byte) 0x02, 0.1234
        );

        codec.encode(map, buf);
        Map<Byte, Number> decoded = codec.decode(buf);

        Assertions.assertEquals(map, decoded);
    }

    @Test
    public void dispatch() {
        ByteCodec<Number> intCodec = ByteCodec.INT.map(i -> i, Number::intValue);
        ByteCodec<Number> doubleCodec = ByteCodec.DOUBLE.map(d -> d, Number::doubleValue);

        KeyDispatchCodec<Byte, Number> codec = new KeyDispatchCodec<>(ByteCodec.BYTE, key -> {
            if (key == 0x01) {
                return intCodec;
            } else if (key == 0x02) {
                return doubleCodec;
            }
            throw new IllegalArgumentException("Unknown key: " + key);
        }, value -> {
            if (value instanceof Integer) {
                return (byte) 0x01;
            } else if (value instanceof Double) {
                return (byte) 0x02;
            }
            throw new IllegalArgumentException("Unknown value: " + value);
        });

        codec.encode(0x1234, buf);
        codec.encode(0.1234, buf);

        Number decodedInt = codec.decode(buf);
        Number decodedDouble = codec.decode(buf);

        Assertions.assertEquals(0x1234, decodedInt);
        Assertions.assertEquals(0.1234, decodedDouble);

        Assertions.assertThrows(IllegalArgumentException.class, () -> codec.encode(0.1234f, buf));
    }
}
