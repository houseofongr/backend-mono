package com.aoo.admin.application.port.in.universe;

import com.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.domain.universe.PublicStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.aoo.admin.domain.universe.Category.LIFE;
import static org.assertj.core.api.AssertionsForClassTypes.*;

class UpdateUniverseCommandTest {

    @Test
    @DisplayName("제목(100자/NB) 조건 확인")
    void testTitleCondition() {
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);

        assertThatThrownBy(() -> new UpdateUniverseCommand(emptyTitle, null, null,null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(blankTitle, null, null,null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(length100, null, null,null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("내용(5000자) 조건 확인")
    void testDescriptionCondition() {
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new UpdateUniverseCommand("우주", length5000, null,null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("태그개수 확인하기(10개, 500자)")
    void testTagCondition() {
        List<String> tag11 = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        List<String> tagLength500 = List.of("a".repeat(501));

        assertThatThrownBy(() -> new UpdateUniverseCommand( null, null, null,null, null, tag11)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(null, null, null,null, null, tagLength500)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("정상 요청")
    void happyCase() {
        UpdateUniverseCommand command = new UpdateUniverseCommand("오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", 1L, LIFE, PublicStatus.PRIVATE, List.of("오르트구름", "태양계", "윤하", "별"));

        assertThat(command).isNotNull();
    }
}