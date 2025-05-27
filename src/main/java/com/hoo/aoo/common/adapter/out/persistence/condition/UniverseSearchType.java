package com.hoo.aoo.common.adapter.out.persistence.condition;

import com.hoo.aoo.admin.domain.universe.Category;

import java.util.Arrays;

public enum UniverseSearchType {
    CONTENT, AUTHOR, ALL;

    public static boolean contains(String s) {
        return Arrays.stream(UniverseSearchType.values()).anyMatch(searchType -> s.equalsIgnoreCase(searchType.name()));
    }
}
