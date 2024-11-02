package LearnMate.dev.controller;

import LearnMate.dev.common.ApiResponse;
import LearnMate.dev.model.dto.request.UserSignInRequest;
import LearnMate.dev.model.dto.request.UserSignUpRequest;
import LearnMate.dev.model.dto.response.UserSignInResponse;
import LearnMate.dev.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ApiResponse<String> signUp(@RequestBody @Valid UserSignUpRequest request) {

        return ApiResponse.onSuccess(userService.signUp(request));

    }

    @GetMapping("/signIn")
    public ApiResponse<UserSignInResponse> signIn(@RequestBody @Valid UserSignInRequest request) {

        return ApiResponse.onSuccessData("로그인 성공", userService.signIn(request));

    }

}
