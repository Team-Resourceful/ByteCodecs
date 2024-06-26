import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    protected ByteBuf buf;

    @BeforeEach
    public void setup() {
        buf = Unpooled.buffer();
    }

    @AfterEach
    public void teardown() {
        buf.release();
    }

}
