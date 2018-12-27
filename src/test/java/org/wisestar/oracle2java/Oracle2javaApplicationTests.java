package org.wisestar.oracle2java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Oracle2javaApplicationTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void contextLoads() {
//		jdbcTemplate.execute("SELECT * FROM A_TEST");
        Integer c = jdbcTemplate.queryForObject("select count(*) from ZH_ST_B", Integer.class);
        System.out.println(c);
	}

}
