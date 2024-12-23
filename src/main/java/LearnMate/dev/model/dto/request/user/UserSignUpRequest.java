package LearnMate.dev.model.dto.request.user;

import LearnMate.dev.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignUpRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    @NotBlank(message = "아이디는 필수입니다.")
    private String loginId;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    public User toEntity(String encodedPassword) {

        return User.builder()
                .name(name)
                .loginId(loginId)
                .password(encodedPassword)
                .build();
    }
}
