package spring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DB Connection 자원 관련 Bean 설정 Class
 * DBCP: HikariCP
 * JDBC: MariaDB or PostgreSQL
 *
 * @author 서영석
 * @since 2022.09.01
 */
@Configuration
public class DataSourceContextConfig {

    /**
     * HikariCP(DBCP) Config(설정 세팅) 객체 Bean 설정
     *
     * @author 서영석
     * @since 2022.09.01
     */
    @Bean(name = "mainHikariConfig")
    public HikariConfig getMainHikariConfig () {
        HikariConfig hikariConfig = new HikariConfig();
        /* MariaDB
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mariadb://localhost:3306/test");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("1234");
        */
        /* PostgreSQL*/
//        hikariConfig.setDriverClassName("org.postgresql.Driver");
//        hikariConfig.setJdbcUrl("jdbc:log4jdbc:postgresql://210.180.118.83:5432/mold_management_system?currentSchema=mold_management_system_v1_8");
//        hikariConfig.setUsername("takensoft");
//        hikariConfig.setPassword("ts44301236!@");
        hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        hikariConfig.setJdbcUrl("jdbc:log4jdbc:postgresql://210.180.118.83:5432/mold_management_system?currentSchema=mold_management_system_v1_8");
        hikariConfig.setUsername("takensoft");
        hikariConfig.setPassword("ts44301236!@");
        return hikariConfig;
    }

    /**
     * HikariCP(DBCP) 객체 Bean 설정
     *
     * @author 서영석
     * @since 2022.09.01
     */
    @Bean(name = "mainHikariDataSource")
    public HikariDataSource getMainHikariDataSource () {
        HikariDataSource hikariDataSource = new HikariDataSource(getMainHikariConfig());
        return hikariDataSource;
    }
}
