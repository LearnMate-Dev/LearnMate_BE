package LearnMate.dev.service;

import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.model.dto.request.UserSignInRequest;
import LearnMate.dev.model.dto.request.UserSignUpRequest;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.repository.UserRepository;
import LearnMate.dev.security.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;

    @Transactional
    public String signUp(UserSignUpRequest request) {

        validIsExistingUser(request.getLoginId());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userRepository.save(request.toEntity(encodedPassword));

        return "회원가입 성공";
    }

    public String signIn(UserSignInRequest request, HttpServletResponse response, HttpSession session) {

        User user = findUserByLoginId(request.getLoginId());
        validPassword(request.getPassword(), user.getPassword());

        // AccessToken 발급 및 응답 헤더에 추가
        String accessToken = jwtProvider.generateAccessToken(user);
        jwtProvider.setAccessTokenInCookie(user, accessToken, response);

        // RefreshToken 발급 및 세션에 저장
        jwtProvider.storeRefreshTokenInSession(user, session);

        return "로그인 성공";

    }

    private void validPassword(String password, String originPassword) {
        if (!passwordEncoder.matches(password, originPassword)) {
            throw new ApiException(ErrorStatus._WRONG_PASSWORD);
        }
    }

    private void validIsExistingUser(String loginId) {
        if (userRepository.findUserByLoginId(loginId).isPresent())
            throw new ApiException(ErrorStatus._USER_ALREADY_EXIST);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findUserByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
    }

}