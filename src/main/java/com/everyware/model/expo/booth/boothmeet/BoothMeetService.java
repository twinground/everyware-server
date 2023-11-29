package com.everyware.model.expo.booth.boothmeet;

import com.everyware.common.exception.BoothHandler;
import com.everyware.common.exception.UserHandler;
import com.everyware.common.jwt.SecurityUtil;
import com.everyware.common.response.status.ErrorStatus;
import com.everyware.model.expo.booth.booth.dto.BoothMeetResponseDTO;
import com.everyware.model.member.Member;
import com.everyware.model.member.repository.MemberRepository;
import com.twilio.type.PhoneNumber;
import com.everyware.model.expo.booth.booth.service.BoothService;
import com.everyware.model.expo.booth.booth.entity.Booth;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoothMeetService {
    private final BoothService boothService;
    private final BoothMeetRepository boothMeetRepository;
    private final MemberRepository memberRepository;
    @Value("${myapp.api.sid}")
    private String ACCOUNT_SID;
    @Value("${myapp.api.token}")
    private String AUTH_TOKEN;

    public BoothMeetResponseDTO makeBoothReservation(Long boothId) {
        Booth booth = boothService.findById(boothId);
        Member member = getMemberFromToken();
        if (boothMeetRepository.existsByBoothAndMember(booth,member)){
            throw new BoothHandler(ErrorStatus.BOOTH_MEETING_ALREADY_EXISTS_IN_BOOTH);
        }
        if(!boothMeetRepository.existsByBoothAndMeetReserveTimeAfter(booth, LocalDateTime.now())){
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime nextReservationTime = currentTime.plusMinutes(10 - (currentTime.getMinute() % 10));
            validateExistBetweenReserveTime(member,nextReservationTime);
            BoothMeet boothMeet = new BoothMeet(booth,nextReservationTime,member);
            boothMeetRepository.save(boothMeet);
            /*
            String boothPhoneNumber ="+82" + booth.getAdminPhoneNumber();
            wilio.init(ACCOUNT_SID, AUTH_TOKEN);
            String messageWithLink = String.format("새로운 커피챗 신청이 들어왔습니다.\nLink: %s\n- Everyware -", booth.getMeetLink());
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(boothPhoneNumber),
                            new com.twilio.type.PhoneNumber("+19404684118"),
                            messageWithLink)
                    .create();

             */
            return BoothMeetResponseDTO.from(boothMeet);
        }
        else {
            // 가장 최근에 예약된 BoothMeet 가져오기
            BoothMeet latestReservation = boothMeetRepository.findTopByBoothOrderByMeetReserveTimeDesc(booth);
            // 예약된 시간이 없거나, 현재 시간보다 이후일 경우에만 예약 생성
            if (latestReservation != null && latestReservation.getMeetReserveTime().isAfter(LocalDateTime.now())) {
                // 가장 최근에 예약된 시간을 기준으로 10분 단위로 다음 예약 시간 계산
                LocalDateTime nextReservationTime = latestReservation.getMeetReserveTime().plusMinutes(10 - (latestReservation.getMeetReserveTime().getMinute() % 10));
                validateExistBetweenReserveTime(member,nextReservationTime);
                BoothMeet boothMeet = new BoothMeet(booth, nextReservationTime,member);
                boothMeetRepository.save(boothMeet);
                return BoothMeetResponseDTO.from(boothMeet);
            }
        }
        throw new BoothHandler(ErrorStatus.BOOTH_MEETING_ERROR);


    }

    private void validateExistBetweenReserveTime(Member member,LocalDateTime reserveTime){
        LocalDateTime endTime = reserveTime.plusMinutes(14);
        if (boothMeetRepository.existsByMemberAndMeetReserveTimeBetween(member,reserveTime.minusMinutes(4),endTime)){
            throw new BoothHandler(ErrorStatus.BOOTH_MEETING_ALREADY_EXISTS_IN_TIME);
        }
    }
    private Member getMemberFromToken() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserHandler(ErrorStatus._UNAUTHORIZED));
        return member;
    }
}
