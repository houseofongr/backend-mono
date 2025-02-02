package com.hoo.aoo.admin;

import com.hoo.aoo.common.adapter.in.web.config.SystemTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@SystemTest
@Sql("classpath:/sql/clear.sql")
@Sql("HouseSystemTest.sql")
public class HouseSystemTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("case1 : 하우스 정상 생성, 수정 및 삭제")
    void testHappyCase() {

        /* 1. 하우스 생성 */

        // language=JSON
        String metadata = """
                {
                      "house": {
                        "title": "cozy house",
                        "author": "leaf",
                        "description": "this is cozy house.",
                        "houseForm": "house",
                        "borderForm": "border",
                        "width": 5000.0,
                        "height": 5000.0
                      },
                      "rooms": [
                        {
                          "form": "room1",
                          "name": "거실",
                          "x": 123.0,
                          "y": 456.0,
                          "z": 1.0,
                          "width": 100.0,
                          "height": 200.0
                        },
                        {
                          "form": "room2",
                          "name": "안방",
                          "x": 234.0,
                          "y": 345.0,
                          "z": 2.0,
                          "width": 200.0,
                          "height": 200.0
                        }
                      ]
                }
                """;

        /* 2. 하우스 리스트 조회 */

        /* 3. 하우스 수정 */

        /* 4. 하우스 리스트 조회 */

        /* 5. 하우스 삭제 */
    }

    private ResponseEntity<?> whenPostHouse(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        return restTemplate
                .exchange(
                        "/admin/houses",
                        HttpMethod.POST,
                        request,
                        Void.class);
    }
}
