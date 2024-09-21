package practice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import practice.domain.Member;
import practice.models.MemberJoinRequestDTO;
import practice.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String showAllMembers(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createForm";
    }

    @PostMapping("/members/new")
    public String createMember(MemberJoinRequestDTO requestDTO) {
        Member member = new Member();
        member.setName(requestDTO.getName());

        memberService.join(member);

        log.info("New member created : " + member);
        return "redirect:/";
    }
}
