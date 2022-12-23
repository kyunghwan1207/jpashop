package com.springstudy.jpashop.api;

import com.springstudy.jpashop.domain.Member;
import com.springstudy.jpashop.domain.dto.*;
import com.springstudy.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest dto){
        Member member = new Member();
        member.setName(dto.getName());
        Long joinedId = memberService.join(member);
        return new CreateMemberResponse(joinedId);
    }
    @PutMapping("/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest dto){
        memberService.update(id, dto.getName());
        Member findMember = memberService.finOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
    @GetMapping
    public Result showAllMembers(){
        List<Member> findmembers = memberService.findMembers();
        List<MemberDto> collect = findmembers.stream().map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

}
