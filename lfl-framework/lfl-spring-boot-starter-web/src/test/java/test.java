import org.junit.jupiter.api.Test;

public class test {

    @Test
    public void StringTest() {
        String token = "Bearer hello myToken";
        int index = token.indexOf("Bearer ");
        System.out.println(index);
    }
}
