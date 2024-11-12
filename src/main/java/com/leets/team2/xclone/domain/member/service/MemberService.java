package com.leets.team2.xclone.domain.member.service;

import com.leets.team2.xclone.domain.member.dto.MemberDTO;
import com.leets.team2.xclone.domain.member.dto.responses.MemberFindGetResponse;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.repository.MemberRepository;
import com.leets.team2.xclone.exception.NoSuchMemberException;

import java.util.List;
import java.util.Optional;

import com.leets.team2.xclone.image.service.ImageSaveService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

  private static final Log log = LogFactory.getLog(MemberService.class);
  private final MemberRepository memberRepository;
  private final ImageSaveService imageSaveService;

  public boolean checkMemberExistsBy(String nickname, Long kakaoId) {
    return this.memberRepository.existsByNicknameAndKakaoId(nickname, kakaoId);
  }

  public Member findMemberBy(String nickname, Long kakaoId) {
    return this.memberRepository.findByNicknameAndKakaoId(nickname, kakaoId).orElseThrow(
        NoSuchMemberException::new);
  }

  public void saveMember(Member member) {
    this.memberRepository.save(member);
  }

  public boolean checkMemberExistsBy(String tag) {
    return this.memberRepository.existsByTag(tag);
  }

  public boolean checkMemberExistsByKakaoId(Long kakaoId) {
    return this.memberRepository.existsByKakaoId(kakaoId);
  }

  public Optional<Member> findMemberByKakaoId(Long kakaoId) {
    return this.memberRepository.findByKakaoId(kakaoId);
  }

  public Member findMemberByTag(String tag){
    return this.memberRepository.findByTag(tag).orElseThrow(
            NoSuchMemberException::new);
  }

  public MemberFindGetResponse findMembersByTag(String tag) {
    MemberFindGetResponse memberFindGetResponse = MemberFindGetResponse.empty();

    this.memberRepository.findByTagContaining(tag).forEach(memberFindGetResponse::add);
    return memberFindGetResponse;
  }

  public MemberDTO.Response updateProfilePicture(Member currentMember, MultipartFile image) {

    if(!image.isEmpty()){
      List<String> imageUrl = imageSaveService.uploadImages(List.of(image));
      currentMember.setImage(imageUrl.get(0));
    }
    memberRepository.save(currentMember);
    return MemberDTO.Response.builder()
            .birthDate(currentMember.getBirthDate())
            .tag(currentMember.getTag())
            .nickname(currentMember.getNickname())
            .introduction(currentMember.getIntroduction())
            .profilePicture(currentMember.getImageUrl().get(0))
            .build();
  }
}
