package ro.rasel.server.security.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDetailsJDBCRepository implements UserDetailsRepository {

    public static final String GET_USER_SQL = "SELECT * FROM users WHERE user_name = ?";
    public static final String GET_USER_ROLES_SQL = "SELECT role FROM user_roles WHERE user_name = ?";
    private final JdbcTemplate jdbcTemplate;

    UserDetailsJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findUserByName(String username) {
        List<String> roles = jdbcTemplate.queryForList(GET_USER_ROLES_SQL, String.class,
                username);

        RowMapper<User> userDetailsRowMapper = (rs, i) -> new User(
                rs.getString("user_name"),
                rs.getString("password"),
                rs.getBoolean("enabled"),
                rs.getBoolean("enabled"),
                rs.getBoolean("enabled"),
                rs.getBoolean("enabled"),
                AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()])));

        return jdbcTemplate.queryForObject(
                GET_USER_SQL, userDetailsRowMapper,
                username);
    }
}
