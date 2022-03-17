package uz.pdp.dto.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class LoginDto {
    private String username;
    private String password;
}
