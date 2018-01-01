package ro.rasel.server.security.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsJDBCDao implements UserDetasDao {

    private final JdbcTemplate jdbcTemplate;

    UserDetailsJDBCDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findUserByName(String username) {
        RowMapper<User> userDetailsRowMapper = (rs, i) -> new User(
                rs.getString("ACCOUNT_NAME"),
                rs.getString("PASSWORD"),
                rs.getBoolean("ENABLED"),
                rs.getBoolean("ENABLED"),
                rs.getBoolean("ENABLED"),
                rs.getBoolean("ENABLED"),
                AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));

        return jdbcTemplate.queryForObject(
                "select * from ACCOUNT where ACCOUNT_NAME = ?", userDetailsRowMapper,
                username);
    }
}
