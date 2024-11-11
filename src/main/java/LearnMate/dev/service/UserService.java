package LearnMate.dev.service;

import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.model.dto.request.UserSignInRequest;
import LearnMate.dev.model.dto.request.UserSignUpRequest;
import LearnMate.dev.model.dto.response.UserSignInResponse;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.repository.UserRepository;
import LearnMate.dev.security.jwt.JwtProvider;
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
        if (userRepository.findUserByLoginId(request.getLoginId()) != null) {
            throw new ApiException(ErrorStatus._ACCOUNT_ALREADY_EXIST);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userRepository.save(request.toEntity(encodedPassword));

        return "회원가입 성공";
    }

    public UserSignInResponse signIn(UserSignInRequest request) {

        User user = userRepository.findUserByLoginId(request.getLoginId());
        if (user == null) {
            throw new ApiException(ErrorStatus._ACCOUNT_NOT_FOUND);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorStatus._WRONG_PASSWORD);
        }

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        return UserSignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._ACCOUNT_NOT_FOUND));
    }

}