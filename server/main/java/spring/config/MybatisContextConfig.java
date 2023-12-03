package spring.config;

import com.zaxxer.hikari.HikariDataSource;
import common.util.bean.BeanUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 관련 Bean 설정 Class
 *
 * @author 서영석
 * @since 2023.10.24
 */
@Configuration
//@MapperScan(basePackages = {"kr.co"}) <-- 이거 대신 mainMapperScannerConfigurer bean 등록
public class MybatisContextConfig {

    //Mybatis 동작 설정
    //mybatis-config.xml 작성 대신 Class 활용
    private class Configuration extends org.apache.ibatis.session.Configuration {
        private Configuration() {
            super();
            super.setCacheEnabled(true);//mapper 캐시 전역 사용여부
            super.setLazyLoadingEnabled(false);//mybatis 지연 로딩 사용여부
            super.setMultipleResultSetsEnabled(true);//한개의 구문에서 여러개의 ResultSet 허용 여부
            super.setUseColumnLabel(true);//컬럼명 대신 컬럼라벨 사용 여부
            super.setUseGeneratedKeys(false);//키 자동 생성
            super.setDefaultExecutorType(ExecutorType.SIMPLE);
            super.setDefaultStatementTimeout(25000);
            super.setCallSettersOnNulls(true);//조회 조회시, null값 무조건 허용

            //Mybatis SQL 작성시, 자주 활용하는 Class 별칭 세팅
            TypeAliasRegistry typeAliasRegistry = super.getTypeAliasRegistry();
            typeAliasRegistry.registerAlias("Boolean", java.lang.Boolean.class);
            typeAliasRegistry.registerAlias("Short", java.lang.Short.class);
            typeAliasRegistry.registerAlias("Integer", java.lang.Integer.class);
            typeAliasRegistry.registerAlias("Long", java.lang.Long.class);
            typeAliasRegistry.registerAlias("Double", java.lang.Double.class);
            typeAliasRegistry.registerAlias("String", java.lang.String.class);
            typeAliasRegistry.registerAlias("HashMap", java.util.HashMap.class);
            typeAliasRegistry.registerAlias("LinkedHashMap", java.util.LinkedHashMap.class);
        }
    }
    //Mybatis 동작 설정 객체 생성
    private Configuration configuration = new Configuration();

    /**
     * Main
     * Mapper : SQL이 작성된 문서와, 작성된 SQL을 실행시킬 class(Mapper)를 매핑 시켜주기 위한 객체 Been 설정 (Main)
     *
     * @author 서영석
     * @since 2023.10.24
     */
    @Bean(name = "mainMapperScannerConfigurer")
    public MapperScannerConfigurer getMainMapperScannerConfigurer () {
        MapperScannerConfigurer mapperScannerConfigurer = null;
        mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("kr.co.carrot");
        mapperScannerConfigurer.setAnnotationClass(org.apache.ibatis.annotations.Mapper.class);//Annotation을 지정해 줘야 service interface 를 Mapper라고 인식하지 않음
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("mainSqlSessionTemplate");
        return mapperScannerConfigurer;
    }

    /**
     * Main
     * Mybatis SqlSessionFactory Bean 설정
     *
     * @author 서영석
     * @since 2023.10.24
     */
    @Bean(name = "mainSqlSessionFactory")
    public SqlSessionFactory getMainSqlSessionFactory (ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource((HikariDataSource) BeanUtil.getBean("mainHikariDataSource"));
        sqlSessionFactoryBean.setConfiguration(new Configuration());
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/main/*-SQL.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * Main
     * Mybatis SqlSessionTemplate Bean 설정
     *
     * @author 서영석
     * @since 2023.10.24
     */
    @Bean(name = "mainSqlSessionTemplate")
    public SqlSessionTemplate getMainSqlSessionTemplate (ApplicationContext applicationContext) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(getMainSqlSessionFactory(applicationContext));
        return sqlSessionTemplate;
    }



}
