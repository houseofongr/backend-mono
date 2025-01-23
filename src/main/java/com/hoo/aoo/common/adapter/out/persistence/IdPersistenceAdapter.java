package com.hoo.aoo.common.adapter.out.persistence;

import com.hoo.aoo.common.application.port.out.IssueIdPort;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdPersistenceAdapter implements IssueIdPort {

    private final EntityManager entityManager;

    @Value("${database.mysql.scheme}")
    private String schema;

    @Override
    public Long issueHouseId() {
        return issue("HOUSE");
    }

    @Override
    public Long issueRoomId() {
        return issue("ROOM");
    }

    private Long issue(String tableName) {
        Object singleResult = entityManager.createNativeQuery(
                        """
                                SELECT AUTO_INCREMENT
                                FROM information_schema.TABLES
                                WHERE TABLE_SCHEMA = :schema
                                AND TABLE_NAME = :tableName
                                """)
                .setParameter("schema", schema)
                .setParameter("tableName", tableName)
                .getSingleResult();

        return (singleResult == null)? 1L : (long) singleResult;
    }
}
