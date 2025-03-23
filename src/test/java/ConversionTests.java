import com.teamresourceful.bytecodecs.base.ByteCodec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ConversionTests extends TestBase {

    @Test
    public void bytes() {
        byte value = 0x05;
        ByteCodec.BYTE.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.BYTE.decode(buf));
    }

    @Test
    public void shorts() {
        short value = 0x1234;
        ByteCodec.SHORT.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.SHORT.decode(buf));
    }

    @Test
    public void ints() {
        int value = 0x12345678;
        ByteCodec.INT.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.INT.decode(buf));
    }

    @Test
    public void longs() {
        long value = 0x1234567890ABCDEFL;
        ByteCodec.LONG.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.LONG.decode(buf));
    }

    @Test
    public void floats() {
        float value = 0.123456789f;
        ByteCodec.FLOAT.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.FLOAT.decode(buf));
    }

    @Test
    public void doubles() {
        double value = 0.1234567890123456789;
        ByteCodec.DOUBLE.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.DOUBLE.decode(buf));
    }

    @Test
    public void booleans() {
        boolean value = true;
        ByteCodec.BOOLEAN.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.BOOLEAN.decode(buf));
    }

    @Test
    public void chars() {
        char value = 'a';
        ByteCodec.CHAR.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.CHAR.decode(buf));
    }

    @Test
    public void strings() {
        String value = "This is a test";
        ByteCodec.STRING.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.STRING.decode(buf));
    }

    @Test
    public void varints() {
        int value = 0x12345678;
        ByteCodec.VAR_INT.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.VAR_INT.decode(buf));
    }

    @Test
    public void varlongs() {
        long value = 0x1234567890ABCDEFL;
        ByteCodec.VAR_LONG.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.VAR_LONG.decode(buf));
    }

    @Test
    public void zigzagvarints() {
        int value = -0x12345678;
        ByteCodec.ZIGZAG_VAR_INT.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.ZIGZAG_VAR_INT.decode(buf));
    }

    @Test
    public void uuids() {
        UUID value = new UUID(0x1234567890ABCDEFL, 0x1234567890ABCDEFL);
        ByteCodec.UUID.encode(value, buf);
        Assertions.assertEquals(value, ByteCodec.UUID.decode(buf));
    }

}
