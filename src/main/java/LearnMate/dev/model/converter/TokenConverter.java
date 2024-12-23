package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.token.TokenDto;

public class TokenConverter {
    public static TokenDto toToken(String token) {
        return TokenDto.builder()
                .accessToken(token)
                .build();
    }
}
