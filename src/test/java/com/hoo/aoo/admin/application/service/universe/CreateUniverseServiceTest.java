package com.hoo.aoo.admin.application.service.universe;

import com.hoo.aoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.hoo.aoo.admin.application.port.out.universe.CreateUniversePort;
import com.hoo.aoo.admin.application.port.out.universe.SaveUniversePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.admin.domain.universe.PublicStatus;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPublicAudioUseCase;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import com.hoo.aoo.file.domain.FileSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUniverseServiceTest {

    CreateUniverseService sut;

    CreateUniversePort createUniversePort = mock();
    UploadPublicImageUseCase uploadPublicImageUseCase = mock();
    UploadPublicAudioUseCase uploadPublicAudioUseCase = mock();
    SaveUniversePort saveUniversePort = mock();

    @BeforeEach
    void init() {
        sut = new CreateUniverseService(createUniversePort, uploadPublicImageUseCase, uploadPublicAudioUseCase, saveUniversePort);
    }

    @Test
    @DisplayName("썸네일, 썸뮤직 파일 누락")
    void testFileOmissionOrDuplicateName() {
        // given
        CreateUniverseCommand command = new CreateUniverseCommand("우주", "유니버스는 우주입니다.", List.of("우주", "행성", "지구", "별"), Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC);
        byte[] content = new byte[2 * 1024 * 1024 + 1];

        // when
        Map<String, MultipartFile> empty = Map.of();
        Map<String, MultipartFile> noThumbnail = Map.of("thumbMusic", new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes()));
        Map<String, MultipartFile> noThumbMusic = Map.of("thumbnail", new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes()));
        Map<String, MultipartFile> exceedThumbnailSize = Map.of("thumbnail", new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", content), "thumbMusic", new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes()));
        Map<String, MultipartFile> exceedThumbMusicSize = Map.of("thumbnail", new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes()), "thumbMusic", new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", content));

        // then
        assertThatThrownBy(() -> sut.create(command, empty)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> sut.create(command, noThumbnail)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> sut.create(command, noThumbMusic)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> sut.create(command, exceedThumbnailSize)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> sut.create(command, exceedThumbMusicSize)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
    }

    @Test
    @DisplayName("잘못된 요청 파라미터")
    void testBadCommand() {
        // given
        Map<String, MultipartFile> map = new HashMap<>();
        map.put("thumbnail", new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes()));
        map.put("thumbMusic", new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes()));

        // when
        CreateUniverseCommand nullTitle = new CreateUniverseCommand(null, "유니버스는 우주입니다.", List.of("우주", "행성", "지구", "별"), Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC);
        CreateUniverseCommand emptyTitle = new CreateUniverseCommand("", "유니버스는 우주입니다.", List.of("우주", "행성", "지구", "별"), Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC);
        CreateUniverseCommand blankTitle = new CreateUniverseCommand(" ", "유니버스는 우주입니다.", List.of("우주", "행성", "지구", "별"), Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC);
        CreateUniverseCommand exceedTagCount = new CreateUniverseCommand("우주", "유니버스는 우주입니다.", List.of("소프트웨어개발", "백엔드개발", "프로그래밍", "자바개발", "웹개발", "데이터베이스설계", "도메인주도설계", "클린코드", "마이크로서비스아키텍처", "헥사고날아키텍처", "+1"), Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC);
        CreateUniverseCommand exceedTagSize = new CreateUniverseCommand("우주", "유니버스는 우주입니다.", List.of("우주", "행성", "지구", "별", "소프트웨어개발_백엔드개발_프로그래밍_자바개발_웹개발_데이터베이스설계_도메인주도설계_클린코드_마이크로서비스아키텍처_헥사고날아키텍처_스프링부트_JPA_RESTAPI_클라우드네이티브_쿠버네티스_도커_CI_CD_데브옵스_유닛테스트_리팩토링_코딩베스트프랙티스_시스템아키텍처_대규모트래픽처리_비동기처리_이벤트드리븐아키텍처_메시지큐_Kafka_RabbitMQ_멀티스레드_고성능시스템_자동화_테스트주도개발_객체지향설계_SOLID원칙_리눅스서버관리_보안_OAuth2_JWT_SpringSecurity_데이터암호화_네트워크보안_API게이트웨이_서버리스_AWS_Azure_GoogleCloud_애자일개발_스프린트_Scrum_프로젝트관리_코드리뷰_Git_버전관리_소프트웨어엔지니어링_기술면접_커리어성장_기술블로그_개발자커뮤니티_오픈소스기여_기술트렌드_AI_머신러닝_딥러닝_컴퓨터비전_NLP_데이터사이언스_빅데이터_SQL최적화_NoSQL_Redis_MongoDB_PostgreSQL_MySQL_OracleDB_분산처리_캐시전략_CDN_API설계_HTTP2_GraphQL_웹소켓_실시간데이터_IoT_블록체인_스마트컨트랙트_제로트러스트보안_AR_VR_메타버스_게임개발_유니티_언리얼엔진_자동차소프트웨어_임베디드개발_ADAS_자율주행_디지털트윈_사물인터넷보안_사이드프로젝트_스타트업개발_기술창업_프로그래머라이프_엔지니어링문화_소프트웨어테스트_퍼포먼스튜닝_코딩챌린지_알고리즘_자료구조_개발자로살아남기_IT업계트렌드_기술리더십_개발자네트워킹_기술컨퍼런스_온라인코딩테스트_FAANG면접_이직준비_커리어전환_데이터분석_개발자성장_기술커뮤니케이션_테크라이팅_개발자멘토링_기술교육_혁신_지속적인학습"), Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC);

        // then
        assertThatThrownBy(() -> sut.create(nullTitle, map)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_PARAMETER.getMessage());
        assertThatThrownBy(() -> sut.create(emptyTitle, map)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_PARAMETER.getMessage());
        assertThatThrownBy(() -> sut.create(blankTitle, map)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_PARAMETER.getMessage());
        assertThatThrownBy(() -> sut.create(exceedTagCount, map)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_PARAMETER.getMessage());
        assertThatThrownBy(() -> sut.create(exceedTagSize, map)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_PARAMETER.getMessage());
    }

    @Test
    @DisplayName("유니버스 생성 서비스")
    void testUniverseCreateService() {
        // given
        CreateUniverseCommand command = new CreateUniverseCommand("우주", "유니버스는 우주입니다.", List.of("우주", "행성", "지구", "별"), Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC);
        Map<String, MultipartFile> map = new HashMap<>();
        map.put("thumbnail", new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes()));
        map.put("thumbMusic", new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes()));

        // when
        when(uploadPublicImageUseCase.publicUpload(any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(1L, null, "universe_music.mp3", "test1235.mp3", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS))));
        when(uploadPublicAudioUseCase.publicUpload(any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(1L, null, "universe_thumb.png", "test1234.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS))));
        MessageDto messageDto = sut.create(command, map);

        // then
        verify(saveUniversePort, times(1)).save(any());
        assertThat(messageDto.message()).contains("번 유니버스가 생성되었습니다.");
    }

}