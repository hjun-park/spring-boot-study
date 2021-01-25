package com.mera.book.AWS.web;

import com.mera.book.AWS.config.auth.LoginUser;
import com.mera.book.AWS.config.auth.dto.SessionUser;
import com.mera.book.AWS.service.PostsService;
import com.mera.book.AWS.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    /*
        build.gradle에서 지정한 머스테치 스타터 덕분에 컨트롤러에서 문자 반환 시
        앞의 경로와 뒤의 파일 확장자는 자동으로 지정.

        따라서 src/main/resources/templates는 자동으로 앞에 붙고
        뒤에는 .mustache가 붙게 된다.

        이후에 View Resolver가 처리한다.
     */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        /*
           userName을 사용할 수 있게 userName을 model에 저장하는 코드 추가
             - CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성
             - 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있음
         */


        // 아래부분 지워지고 파라미터에 LoginUser 어노테이션이 대신함
        // @LoginUser만 가지고 세션 정보를 가져올 수 있음
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        // 세션이 저장된 값이 있으면 model에 userName으로 등록
        // 세션이 저장된 값이 없으면 model에 아무런 값이 없는 상태라서 로그인 버튼이 보이게 됨
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    /*
        /posts/save를 호출하면 posts-save.mustache를 호출하는 메소드 추가
        컨트롤러 코드가 생성되었다면 posts-save.mustache 파일을 생성함
     */
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }


    // 수정하는 기능 매핑
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }



}
