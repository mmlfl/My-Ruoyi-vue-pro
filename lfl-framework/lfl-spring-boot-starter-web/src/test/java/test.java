import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

public class test {

    @Test
    public void method() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        String encode = passwordEncoder.encode("admin123");
        System.out.println(passwordEncoder.matches(encode,"admin123"));
    }

}
