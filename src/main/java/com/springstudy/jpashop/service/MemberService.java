package com.springstudy.jpashop.service;

import com.springstudy.jpashop.domain.Member;
import com.springstudy.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    /**
     * 회원 가입
     */
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member){
        List<Member> members = memberRepository.findByName(member.getName());
        if(!members.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    /**
     * 회원 목록 조회
     */
    public List<Member> findMembes(){
        return memberRepository.findAll();
    }
    public Member finOne(Long id){
        return memberRepository.findOne(id);
    }
}
