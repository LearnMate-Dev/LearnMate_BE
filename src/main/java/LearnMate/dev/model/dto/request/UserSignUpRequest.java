package LearnMate.dev.model.dto.request;

import LearnMate.dev.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
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

    @Builder
    public UserSignUpRequest(String name, String loginId, String password) {

        this.name = name;
        this.loginId = loginId;
        this.password = password;

    }

    public User toEntity(String encodedPassword) {

        return User.builder()
                .name(name)
                .loginId(loginId)
                .password(encodedPassword)
                .build();

    }

}
