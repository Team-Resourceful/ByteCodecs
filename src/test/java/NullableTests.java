import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

public class NullableTests extends TestBase {

    @Test
    public void testNullable() {
        ByteCodec<String> nullableString = ByteCodec.STRING.nullableOf();

        nullableString.encode(null, buf);
        String decoded = nullableString.decode(buf);
        Assertions.assertNull(decoded);

        nullableString.encode("Hello, World!", buf);
        decoded = nullableString.decode(buf);
        Assertions.assertEquals("Hello, World!", decoded);
    }

    @Test
    public void testNullableField() {
        ByteCodec<Map.Entry<String, String>> nullableField = ObjectByteCodec.create(
                ByteCodec.STRING.fieldOf(Map.Entry::getKey),
                ByteCodec.STRING.nullableOf().fieldOf(Map.Entry::getValue),
                AbstractMap.SimpleEntry::new
        );

        nullableField.encode(new AbstractMap.SimpleEntry<>("key", null), buf);
        Map.Entry<String, String> decoded = nullableField.decode(buf);
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("key", null), decoded);

        nullableField.encode(new AbstractMap.SimpleEntry<>("key", "value"), buf);
        decoded = nullableField.decode(buf);
        Assertions.assertEquals(new AbstractMap.SimpleEntry<>("key", "value"), decoded);
    }
}
