package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.adapter.out.persistence.mapper.UniverseMapper;
import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.aoo.admin.domain.universe.Category;
import com.aoo.admin.domain.universe.PublicStatus;
import com.aoo.admin.domain.universe.TraversalComponents;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.aoo.common.adapter.out.persistence.entity.HashtagJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseHashtagJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseLikeJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.aoo.common.application.service.MockEntityFactoryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("UniversePersistenceAdapterTest.sql")
@PersistenceAdapterTest
@Import({UniversePersistenceAdapter.class, UniverseMapper.class})
class UniversePersistenceAdapterTest {

    @Autowired
    UniversePersistenceAdapter sut;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("해시태그 존재여부 확인 후 생성 테스트")
    void testGetOrCreateHashtag() {
        // given
        String existHashtag = "exist";
        String notExistHashtag = "not_exist";

        // when
        HashtagJpaEntity entity = sut.getOrCreate(existHashtag);
        HashtagJpaEntity entity2 = sut.getOrCreate(notExistHashtag);

        // then
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity2.getId()).isNotEqualTo(1L);
    }

    @Test
    @DisplayName("유니버스 저장 테스트")
    void testSaveUniverse() {
        // given
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        Long id = sut.save(universe).universeId();
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findById(id).orElseThrow();

        // then
        assertThat(universeJpaEntity.getTitle()).isEqualTo(universe.getBasicInfo().getTitle());
        assertThat(universeJpaEntity.getDescription()).isEqualTo(universe.getBasicInfo().getDescription());
        assertThat(universeJpaEntity.getPublicStatus()).isEqualTo(universe.getBasicInfo().getPublicStatus());
        assertThat(universeJpaEntity.getCategory()).isEqualTo(universe.getBasicInfo().getCategory());
        assertThat(universeJpaEntity.getViewCount()).isEqualTo(0L);
        assertThat(universeJpaEntity.getUniverseHashtags()).hasSize(4);
        assertThat(universeJpaEntity.getUniverseLikes()).isEmpty();
        assertThat(universeJpaEntity.getThumbnailFileId()).isEqualTo(universe.getFileInfo().getThumbnailId());
        assertThat(universeJpaEntity.getThumbMusicFileId()).isEqualTo(universe.getFileInfo().getThumbMusicId());
    }

    @Test
    @DisplayName("유니버스 10, 20, 50건 조회")
    void testSearchPageSize() {
        // given
        SearchUniverseCommand command10 = new SearchUniverseCommand(PageRequest.of(0, 10), null, null, null, null, false);
        SearchUniverseCommand command20 = new SearchUniverseCommand(PageRequest.of(0, 20), null, null, null, null, false);
        SearchUniverseCommand command50 = new SearchUniverseCommand(PageRequest.of(0, 50), null, null, null, null, false);

        // when
        SearchUniverseResult resultSize10 = sut.search(command10);
        SearchUniverseResult resultSize20 = sut.search(command20);
        SearchUniverseResult resultSize50 = sut.search(command50);

        // then
        assertThat(resultSize10.universes().size()).isEqualTo(10);
        assertThat(resultSize20.universes().size()).isEqualTo(12);
        assertThat(resultSize50.universes().size()).isEqualTo(12);

        // 페이지네이션 개수 확인
        assertThat(resultSize10.pagination().size()).isEqualTo(10);
        assertThat(resultSize20.pagination().size()).isEqualTo(20);
        assertThat(resultSize50.pagination().size()).isEqualTo(50);

        // 토탈 페이지 개수 확인
        assertThat(resultSize10.pagination().totalPages()).isEqualTo(2);
        assertThat(resultSize20.pagination().totalPages()).isEqualTo(1);
        assertThat(resultSize50.pagination().totalPages()).isEqualTo(1);

        // 카운트 쿼리 확인
        assertThat(resultSize10.pagination().totalElements()).isEqualTo(12);
        assertThat(resultSize20.pagination().totalElements()).isEqualTo(12);
        assertThat(resultSize50.pagination().totalElements()).isEqualTo(12);
    }

    @Test
    @DisplayName("특정 컨텐츠(이름, 내용, 작성자)가 포함된 유니버스 조회")
    void testSearchKeyword() {
        // given
        SearchUniverseCommand 건강 = new SearchUniverseCommand(PageRequest.of(0, 10), "content", "건강", null, null, false);
        SearchUniverseCommand 콘텐츠 = new SearchUniverseCommand(PageRequest.of(0, 10), "content", "콘텐츠", null, null, false);
        SearchUniverseCommand leaf = new SearchUniverseCommand(PageRequest.of(0, 10), "author", "leaf", null, null, false);
        SearchUniverseCommand 건 = new SearchUniverseCommand(PageRequest.of(0, 10), "all", "건", null, null, false);

        // when
        SearchUniverseResult result_건강 = sut.search(건강);
        SearchUniverseResult result_콘텐츠 = sut.search(콘텐츠);
        SearchUniverseResult result_leaf = sut.search(leaf);
        SearchUniverseResult result_건 = sut.search(건);

        // then
        assertThat(result_건강.universes().size()).isEqualTo(3);
        assertThat(result_콘텐츠.universes().size()).isEqualTo(4);
        assertThat(result_leaf.universes().size()).isEqualTo(7);
        assertThat(result_건.universes().size()).isEqualTo(7);
        assertThat(result_건강.universes()).allMatch(universeInfo -> universeInfo.title().contains("건강") || universeInfo.description().contains("건강"));
        assertThat(result_콘텐츠.universes()).allMatch(universeInfo -> universeInfo.title().contains("콘텐츠") || universeInfo.description().contains("콘텐츠"));
        assertThat(result_leaf.universes()).allMatch(universeInfo -> universeInfo.author().contains("leaf"));
        assertThat(result_건.universes()).allMatch(universeInfo -> universeInfo.title().contains("건") || universeInfo.description().contains("건") || universeInfo.author().contains("건"));
    }

    @Test
    @DisplayName("특정 카테고리가 포함된 유니버스 조회")
    void testFilterCategory() {
        // given
        SearchUniverseCommand life = new SearchUniverseCommand(PageRequest.of(0, 10), null, null, Category.LIFE.toString(), null, false);
        SearchUniverseCommand fashion = new SearchUniverseCommand(PageRequest.of(0, 10), null, null, Category.FASHION_AND_BEAUTY.toString(), null, false);

        // when
        SearchUniverseResult result_life = sut.search(life);
        SearchUniverseResult result_fashion = sut.search(fashion);

        // then
        assertThat(result_life.universes().size()).isEqualTo(3);
        assertThat(result_fashion.universes().size()).isEqualTo(3);
        assertThat(result_life.universes()).allMatch(universeInfo -> universeInfo.category().equals(Category.LIFE.toString()));
        assertThat(result_fashion.universes()).allMatch(universeInfo -> universeInfo.category().equals(Category.FASHION_AND_BEAUTY.toString()));
    }

    @Test
    @DisplayName("제목, 등록일자, 조회수 내림차순 / 오름차순 정렬")
    void testOrder() {
        // given
        SearchUniverseCommand 제목_오름차순 = new SearchUniverseCommand(PageRequest.of(0, 20), null, null, null, "title", true);
        SearchUniverseCommand 제목_내림차순 = new SearchUniverseCommand(PageRequest.of(0, 20), null, null, null, "title", false);
        SearchUniverseCommand 등록일자_오름차순 = new SearchUniverseCommand(PageRequest.of(0, 20), null, null, null, "registered_date", true);
        SearchUniverseCommand 등록일자_내림차순 = new SearchUniverseCommand(PageRequest.of(0, 20), null, null, null, "registered_date", false);
        SearchUniverseCommand 조회수_내림차순 = new SearchUniverseCommand(PageRequest.of(0, 20), null, null, null, "views", true);
        SearchUniverseCommand 조회수_오름차순 = new SearchUniverseCommand(PageRequest.of(0, 20), null, null, null, "views", false);

        // when
        SearchUniverseResult result_제목_오름차순 = sut.search(제목_오름차순);
        SearchUniverseResult result_제목_내림차순 = sut.search(제목_내림차순);
        SearchUniverseResult result_조회수_내림차순 = sut.search(조회수_내림차순);
        SearchUniverseResult result_조회수_오름차순 = sut.search(조회수_오름차순);

        Universe universe = MockEntityFactoryService.getUniverse();
        sut.save(universe);

        SearchUniverseResult result_등록일자_내림차순 = sut.search(등록일자_내림차순);
        SearchUniverseResult result_등록일자_오름차순 = sut.search(등록일자_오름차순);

        // then
        assertThat(result_제목_오름차순.universes().getFirst().title()).isEqualTo("건강 유니버스");
        assertThat(result_제목_내림차순.universes().getFirst().title()).isEqualTo("패션 유니버스");
        assertThat(result_제목_오름차순.universes().getLast().title()).isEqualTo("패션 유니버스");
        assertThat(result_제목_내림차순.universes().getLast().title()).isEqualTo("건강 유니버스");

        // 패션 조회수 : 10, 우주 조회수 : 0
        assertThat(result_조회수_내림차순.universes().getLast().title()).isEqualTo("패션 유니버스");
        assertThat(result_조회수_오름차순.universes().getLast().title()).isEqualTo("우주");

        // 정책 등록일자 가장 오래됨, 우주 등록일자 가장 최신
        assertThat(result_등록일자_오름차순.universes().getFirst().title()).isEqualTo("정책 유니버스");
        assertThat(result_등록일자_내림차순.universes().getFirst().title()).isEqualTo("우주");
    }

    @Test
    @DisplayName("유니버스 기본 검색(아무 파라미터 없이 검색 시 → 10건 등록 날짜 내림차순)")
    void testSearch() {
        // given
        Universe universe = Universe.create(1L, 1L, 1L, 1L, 1L, "test title", "test desc", Category.FASHION_AND_BEAUTY, PublicStatus.PRIVATE, List.of());
        sut.save(universe);

        SearchUniverseCommand command = new SearchUniverseCommand(PageRequest.of(0, 10), null, null, null, null, null);

        // when
        SearchUniverseResult result = sut.search(command);

        // then : 가장 최신 등록된 우주가 먼저 조회
        assertThat(result.universes().getFirst().title()).isEqualTo(universe.getBasicInfo().getTitle());
    }

    @Test
    @DisplayName("유니버스 트리 컴포넌트 조회")
    void testFindTreeComponents() {
        // given
        Long universeId = 1L;

        // when
        TraversalComponents treeComponents = sut.findTreeComponents(universeId);

        // then
        assertThat(treeComponents.getUniverse().getId()).isEqualTo(1L);
        assertThat(treeComponents.getUniverse().getFileInfo().getImageId()).isEqualTo(1L);
        assertThat(treeComponents.getSpaces()).hasSize(5);
        assertThat(treeComponents.getPieces()).hasSize(7);
    }

    @Test
    @DisplayName("태그 수정시 기존태그(UniverseHashtag) 삭제")
    void testDeleteOriginalTag() {
        // given
        Universe universe = Universe.load(12L, 13L, 13L, 1L, 1L, "오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", Category.LIFE, PublicStatus.PRIVATE, 0, 0L, List.of("오르트구름", "태양계", "윤하", "별"), ZonedDateTime.now(), ZonedDateTime.now());

        // when
        sut.updateDetail(universe);
        List<UniverseHashtagJpaEntity> entities = em.createQuery("select uh from UniverseHashtagJpaEntity uh where uh.id in (1, 2, 3, 4)", UniverseHashtagJpaEntity.class).getResultList();

        // then
        assertThat(entities).hasSize(1);
        assertThat(entities.getFirst().getHashtag().getTag()).isEqualTo("별");
    }

    @Test
    @DisplayName("정상 수정로직(Happy Case)")
    void happyCase() {
        // given
        Universe universe = Universe.load(12L, 13L, 13L, 1L, 1L, "오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", Category.LIFE, PublicStatus.PRIVATE, 0, 0L, List.of("오르트구름", "태양계", "윤하", "별"), ZonedDateTime.now(), ZonedDateTime.now());

        // when
        sut.updateDetail(universe);
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findById(universe.getId()).orElseThrow();

        // then
        assertThat(universeJpaEntity.getThumbnailFileId()).isEqualTo(13L);
        assertThat(universeJpaEntity.getThumbMusicFileId()).isEqualTo(13L);
        assertThat(universeJpaEntity.getTitle()).isEqualTo("오르트구름");
        assertThat(universeJpaEntity.getDescription()).isEqualTo("오르트구름은 태양계 최외곽에 위치하고 있습니다.");
        assertThat(universeJpaEntity.getCategory()).isEqualTo(Category.LIFE);
        assertThat(universeJpaEntity.getPublicStatus()).isEqualTo(PublicStatus.PRIVATE);
        assertThat(universeJpaEntity.getUniverseHashtags().stream().map(universeHashtagJpaEntity -> universeHashtagJpaEntity.getHashtag().getTag()).toList()).allMatch(tag -> List.of("오르트구름", "태양계", "윤하", "별").contains(tag));
    }

    @Test
    @DisplayName("유니버스 정상 삭제 시 연관된 모든 엔티티 삭제되는지 확인")
    @Transactional
    void testDeleteRelationship() {
        // given
        Universe universe = Universe.load(12L, 12L, 12L, 1L, 1L, "우주", null, null, null, null, null, List.of("오르트구름", "태양계", "윤하", "별"), null, null);

        // when
        sut.delete(universe);
        List<UniverseHashtagJpaEntity> hashtagEntities = em.createQuery("select uh from UniverseHashtagJpaEntity uh where uh.id in (1, 2, 3, 4)", UniverseHashtagJpaEntity.class).getResultList();
        List<UniverseLikeJpaEntity> likeEntities = em.createQuery("select ul from UniverseLikeJpaEntity ul where ul.id in (1, 2, 3, 4)", UniverseLikeJpaEntity.class).getResultList();

        // then
        assertThat(universeJpaRepository.findById(universe.getId())).isEmpty();
        assertThat(hashtagEntities).isEmpty();
        assertThat(likeEntities).isEmpty();
    }
}