package LearnMate.dev.controller;

import LearnMate.dev.common.ApiResponse;
import LearnMate.dev.model.dto.request.UserSignInRequest;
import LearnMate.dev.model.dto.request.UserSignUpRequest;
import LearnMate.dev.model.dto.response.HomeResponse;
import LearnMate.dev.model.dto.response.UserSignInResponse;
import LearnMate.dev.service.UserService;
import com.google.protobuf.Api;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/signIn")
    public ApiResponse<String> signIn(@RequestBody @Valid UserSignInRequest request,
                                      HttpServletResponse response,
                                      HttpSession session) {

        return ApiResponse.onSuccessData("로그인 성공", userService.signIn(request, response, session));

    }

    @GetMapping("/home")
    public ApiResponse<HomeResponse> getHome() {
        return ApiResponse.onSuccessData("홈 화면 조회 성공", userService.getHome());
    }

}
