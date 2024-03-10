package ru.denisaql.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
public class JdbcIntegrator {
    private final NamedParameterJdbcTemplate template;

    public long calc() {
        String s = "select count(*) from tab where id > :id";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", 10);
        return template.queryForObject(s, source, Long.class);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.printf("%d\n", calc());
        System.out.printf("completed\n");
    }
}
