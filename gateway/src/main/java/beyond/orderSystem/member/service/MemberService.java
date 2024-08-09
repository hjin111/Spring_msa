package beyond.orderSystem.member.service;

import beyond.orderSystem.member.domain.Member;
import beyond.orderSystem.member.dto.MemberLoginDto;
import beyond.orderSystem.member.dto.MemberSaveReqDto;
import beyond.orderSystem.member.dto.MemberResDto;
import beyond.orderSystem.member.dto.ResetPassWordDto;
import beyond.orderSystem.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Member create(MemberSaveReqDto dto){

        if(memberRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
        };
        
        Member member = memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
        return member;
    }

    public Page<MemberResDto> list(Pageable pageable){
        Page<Member> members = memberRepository.findAll(pageable);
        Page<MemberResDto> memberResDtos = members.map( a -> a.fromEntity());
        return memberResDtos;
        // return members.map( a -> a.fromEntity());

    }

    public MemberResDto myInfo(){

        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // 이메일을 꺼내기
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() -> new EntityNotFoundException("member is not found"));
        MemberResDto memberResDto = member.fromEntity();
        return memberResDto;

    }


    public Member login(MemberLoginDto dto){

        // email 존재여부 check
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 email 입니다."));
        // password 일치여부
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;

    }

    public void resetPassword(ResetPassWordDto dto){

        // email 존재여부 check
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 email 입니다."));
        // password 일치여부
        if(!passwordEncoder.matches(dto.getAsIsPassword(), member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder.encode(dto.getToBePassword()));

    }

}
