package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.ReadHouseListResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReadHouseListController {

    @GetMapping("/admin/houses")
    public ResponseEntity<ReadHouseListResult> getList(@RequestParam(required = false) Pageable pageable,
                                                       @RequestParam(required = false) String searchType,
                                                       @RequestParam(required = false) String keyword) {

        ReadHouseListResult result = new ReadHouseListResult(new PageImpl<>(
                List.of(
                        new ReadHouseListResult.House(1L, "cozy house", "남상엽", "this is cozy house", "January.15. 2025", "January.15. 2025", 1L),
                        new ReadHouseListResult.House(2L, "2024 아오옹 시그치너 하우스 S/S", "주아랑", "990년 고딩2때 청주에서 전국체전이 열렸다. 그리고 주간야구라는 당시 유일했던 야구잡지사에서 글을 쓰는 기자분이 그라운드안에서 내게 인사를 건냈다. 그리고 몇가지 질문을 하고나서 내게 훗날 좋은 선수가 되길바란다고했다. 그리고 다음해 1991년 여름, 국가대표로 미국에서 활약하고 돌아온 나는 공항에서 일년전 그기자형을 만났다. 당시 다른선수들과는 달리 서울에서 갈곳이 없었던 나를 집에 데리고가서 하루밤을 재워주셨다. 그기자형의 집에 도착해서 나는 기자형의 방안에 있던 책장속에 눈을 뗄수가 없었다. 책장속에는 온갓 영어로만된 미식축구, 농구,야구 잡지들이 가득했다. 그중에서 놀란라이언의 책은 나의 심장을 자극했다. 나는 기자형의 도움으로 대충 책속의 내용을 들으며 사진들을 관찰했다. 나의 그런모습을 보고는 그기자형은 내게 그책을 선물로 주셨다. 미국가서 좋은성적을 낸것보다 몇배 더 좋았다. 그뒤로 난 책속의 놀란라이언을 흉내내기시작했다. 놀란라이언처럼 강속구 투수가 되고싶었다. 그래서 런닝을 많이하고 웨이트트레이닝을 많이 한다는 책속의 내용과 사진들을 따라했다. 그리고 어느덧 난 강속구투구가 되어있었다. 꿈을 갖는다는것 그리고 꿈을 준다는것....그렇게 기자형님과 나는 꿈을 주고 받는 소중한 인연을 이어갔다. 훗날 나는 최초의 코리언 메이저리거가 되었고 그 기자형은 야구전문기자로써 최초의 야구단 사장이 되었다. 소중함과 고마운인연.".substring(0, 100) + "...", "January.15. 2025", "January.16. 2025", 2L)),
                PageRequest.of(1, 9), 2));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
