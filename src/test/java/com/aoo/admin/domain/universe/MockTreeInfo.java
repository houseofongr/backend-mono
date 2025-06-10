package com.aoo.admin.domain.universe;

import com.aoo.admin.domain.universe.space.Space;
import com.aoo.admin.domain.universe.space.piece.Piece;

import java.time.ZonedDateTime;
import java.util.List;

public class MockTreeInfo {

    static Universe universe = Universe.create(1L, 1L, 2L, 3L, 1L, "new universe", "새 유니버스", Category.FASHION_AND_BEAUTY, PublicStatus.PRIVATE, List.of("1", "2", "3"));

    static Space space1 = Space.loadTreeComponent(1L, 4L, 1L, null, "space1", "유니버스의 스페이스-1", 0.5f, 0.5f, 0.7f, 0.6f, ZonedDateTime.now(), ZonedDateTime.now());
    static Space space2 = Space.loadTreeComponent(2L, 5L, 1L, null, "space2", "유니버스의 스페이스-2", 0.4f, 0.2f, 0.5f, 0.1f, ZonedDateTime.now(), ZonedDateTime.now());
    static Space space3 = Space.loadTreeComponent(3L, 6L, 1L, 1L, "space3", "스페이스1의 스페이스-1", 0.2f, 0.3f, 0.4f, 0.2f, ZonedDateTime.now(), ZonedDateTime.now());
    static Space space4 = Space.loadTreeComponent(4L, 7L, 1L, 2L, "space4", "스페이스2의 스페이스-1", 0.7f, 0.4f, 0.3f, 0.3f, ZonedDateTime.now(), ZonedDateTime.now());
    static Space space5 = Space.loadTreeComponent(5L, 8L, 1L, 2L, "space5", "스페이스2의 스페이스-2", 0.8f, 0.1f, 0.2f, 0.4f, ZonedDateTime.now(), ZonedDateTime.now());
    static List<Space> spaces = List.of(space1, space2, space3, space4, space5);

    static Piece piece1 = Piece.loadTreeComponent(1L, -1L, 1L, null, "element1", "유니버스의 엘리먼트-1", 0.5f, 0.5f, 0.7f, 0.6f, ZonedDateTime.now(), ZonedDateTime.now());
    static Piece piece2 = Piece.loadTreeComponent(2L, -1L, 1L, 1L, "element2", "스페이스1의 엘리먼트-1", 0.4f, 0.2f, 0.5f, 0.1f, ZonedDateTime.now(), ZonedDateTime.now());
    static Piece piece3 = Piece.loadTreeComponent(3L, -1L, 1L, 3L, "element3", "스페이스3의 엘리먼트-1", 0.2f, 0.3f, 0.4f, 0.2f, ZonedDateTime.now(), ZonedDateTime.now());
    static Piece piece4 = Piece.loadTreeComponent(4L, -1L, 1L, 4L, "element4", "스페이스4의 엘리먼트-1", 0.7f, 0.4f, 0.3f, 0.3f, ZonedDateTime.now(), ZonedDateTime.now());
    static Piece piece5 = Piece.loadTreeComponent(5L, 9L, 1L, 4L, "element5", "스페이스4의 엘리먼트-2", 0.8f, 0.1f, 0.2f, 0.4f, ZonedDateTime.now(), ZonedDateTime.now());
    static Piece piece6 = Piece.loadTreeComponent(6L, -1L, 1L, 5L, "element6", "스페이스5의 엘리먼트-1", 0.8f, 0.1f, 0.2f, 0.4f, ZonedDateTime.now(), ZonedDateTime.now());
    static Piece piece7 = Piece.loadTreeComponent(7L, -1L, 1L, 5L, "element7", "스페이스5의 엘리먼트-2", 0.8f, 0.1f, 0.2f, 0.4f, ZonedDateTime.now(), ZonedDateTime.now());
    static List<Piece> pieces = List.of(piece1, piece2, piece3, piece4, piece5, piece6, piece7);

    public static TreeInfo getTreeInfo() {
        return TreeInfo.create(universe, spaces, pieces);
    }

    public static TraversalComponents getTraversalComponent() {
        return new TraversalComponents(universe, spaces, pieces);
    }
}
