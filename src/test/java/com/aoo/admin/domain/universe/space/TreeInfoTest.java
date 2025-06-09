package com.aoo.admin.domain.universe.space;

import com.aoo.admin.domain.universe.Category;
import com.aoo.admin.domain.universe.PublicStatus;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.admin.domain.universe.space.element.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TreeInfoTest {

    Universe universe = Universe.create(1L, 1L, 2L, 3L, 1L, "new universe", "새 유니버스", Category.FASHION_AND_BEAUTY, PublicStatus.PRIVATE, List.of("1", "2", "3"));

    Space space1 = Space.create(1L, 4L, 1L, null, "space1", "유니버스의 스페이스-1", 0.5f, 0.5f, 0.7f, 0.6f);
    Space space2 = Space.create(2L, 5L, 1L, null, "space2", "유니버스의 스페이스-2", 0.4f, 0.2f, 0.5f, 0.1f);
    Space space3 = Space.create(3L, 6L, 1L, 1L, "space3", "스페이스1의 스페이스-1", 0.2f, 0.3f, 0.4f, 0.2f);
    Space space4 = Space.create(4L, 7L, 1L, 2L, "space4", "스페이스2의 스페이스-1", 0.7f, 0.4f, 0.3f, 0.3f);
    Space space5 = Space.create(5L, 8L, 1L, 2L, "space5", "스페이스2의 스페이스-2", 0.8f, 0.1f, 0.2f, 0.4f);
    List<Space> spaces = List.of(space1, space2, space3, space4, space5);

    Piece piece1 = Piece.create(1L, 9L, 1L, null, "element1", "유니버스의 엘리먼트-1", 0.5f, 0.5f, 0.7f, 0.6f);
    Piece piece2 = Piece.create(2L, 10L, 1L, 1L, "element2", "스페이스1의 엘리먼트-1", 0.4f, 0.2f, 0.5f, 0.1f);
    Piece piece3 = Piece.create(3L, 11L, 1L, 3L, "element3", "스페이스3의 엘리먼트-1", 0.2f, 0.3f, 0.4f, 0.2f);
    Piece piece4 = Piece.create(4L, 12L, 1L, 4L, "element4", "스페이스4의 엘리먼트-1", 0.7f, 0.4f, 0.3f, 0.3f);
    Piece piece5 = Piece.create(5L, 13L, 1L, 4L, "element5", "스페이스4의 엘리먼트-2", 0.8f, 0.1f, 0.2f, 0.4f);
    Piece piece6 = Piece.create(6L, 14L, 1L, 5L, "element6", "스페이스5의 엘리먼트-1", 0.8f, 0.1f, 0.2f, 0.4f);
    Piece piece7 = Piece.create(7L, 15L, 1L, 5L, "element7", "스페이스5의 엘리먼트-2", 0.8f, 0.1f, 0.2f, 0.4f);
    List<Piece> pieces = List.of(piece1, piece2, piece3, piece4, piece5, piece6, piece7);

    @Test
    @DisplayName("Universe를 Root로 하는 Tree 생성 테스트")
    void testCreateRoot() {

        // when
        TreeInfo root = TreeInfo.create(universe, spaces, pieces);

        // then
        assertThat(root.getParent()).isNull();
        assertThat(root.getDepth()).isEqualTo(0);
        assertThat(root.getUniverseTreeComponent()).isInstanceOf(Universe.class);
        assertThat(root.getChildren()).hasSize(3);
    }

    @Test
    @DisplayName("DFS 탐색")
    void testDFS() {
        // given
        TreeInfo root = TreeInfo.create(universe, spaces, pieces);

        // when
        TreeInfo space1 = root.getComponent(Space.class, 1L).getTreeInfo();
        TreeInfo space2 = root.getComponent(Space.class, 2L).getTreeInfo();
        TreeInfo space3 = root.getComponent(Space.class, 3L).getTreeInfo();
        TreeInfo space4 = root.getComponent(Space.class, 4L).getTreeInfo();
        TreeInfo space5 = root.getComponent(Space.class, 5L).getTreeInfo();
        TreeInfo element1 = root.getComponent(Piece.class, 1L).getTreeInfo();
        TreeInfo element2 = root.getComponent(Piece.class, 2L).getTreeInfo();
        TreeInfo element3 = root.getComponent(Piece.class, 3L).getTreeInfo();
        TreeInfo element4 = root.getComponent(Piece.class, 4L).getTreeInfo();
        TreeInfo element5 = root.getComponent(Piece.class, 5L).getTreeInfo();
        TreeInfo element6 = root.getComponent(Piece.class, 6L).getTreeInfo();
        TreeInfo element7 = root.getComponent(Piece.class, 7L).getTreeInfo();

        // then
        assertThat(root.getComponent(Space.class, 6L)).isNull();
        assertThat(root.getComponent(Piece.class, 8L)).isNull();

        assertDFS(space1, Space.class, 1, 1, root);
        assertDFS(space2, Space.class, 2, 1, root);
        assertDFS(space3, Space.class, 3, 2, space1);
        assertDFS(space4, Space.class, 4, 2, space2);
        assertDFS(space5, Space.class, 5, 2, space2);

        assertDFS(element1, Piece.class, 1, 1, root);
        assertDFS(element2, Piece.class, 2, 2, space1);
        assertDFS(element3, Piece.class, 3, 3, space3);
        assertDFS(element4, Piece.class, 4, 3, space4);
        assertDFS(element5, Piece.class, 5, 3, space4);
        assertDFS(element6, Piece.class, 6, 3, space5);
        assertDFS(element7, Piece.class, 7, 3, space5);
    }

    private void assertDFS(TreeInfo target, Class<?> clazz, long id, int depth, TreeInfo parent) {
        assertThat(target.getDepth()).isEqualTo(depth);
        assertThat(target.getUniverseTreeComponent()).isInstanceOf(clazz);
        assertThat(target.getUniverseTreeComponent().getId()).isEqualTo(id);
        assertThat(target.getParent()).isEqualTo(parent);
    }

}