package LearnMate.dev.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignInResponse {

    private String accessToken;
    private String refreshToken;

    @Builder
    public UserSignInResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
    }

}
