package co.istad.storeistad.controller;


import co.istad.storeistad.model.request.mail.VerifyCodeDto;
import co.istad.storeistad.model.request.mail.VerifyRQ;
import co.istad.storeistad.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class VerifyCodeController {
    private final AuthService authService;
//    @CrossOrigin(origins = "http://localhost:7000/")
    @GetMapping("auth/verify")
    public String viewVerifyCode(ModelMap modelMap,
                                 VerifyCodeDto verifyCodeDto,
                                 @RequestParam String email){
        modelMap.addAttribute("verifyCodeDto",verifyCodeDto);
        modelMap.addAttribute("email",email);
        return "auth/resend-verify-code";
    }
//    @CrossOrigin(origins = "http://localhost:7000/")
    @PostMapping("auth/verify")
    public String doVerifyCode(VerifyCodeDto verifyCodeDto,
                               @RequestParam String email){
        System.out.println(verifyCodeDto.get6Digits());
        String sixDigits = verifyCodeDto.get6Digits();

        authService.verifyRQ(new VerifyRQ(email,sixDigits));
        return "auth/verify-succeed";
    }
}
