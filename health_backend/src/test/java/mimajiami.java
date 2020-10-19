import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class mimajiami {
    public static void main(String[] args) {
        // springsecurity 注册加密方法
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("1234");
        System.out.println(encode);

    }
}
