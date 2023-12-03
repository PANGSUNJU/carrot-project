package spring.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zaxxer.hikari.HikariDataSource;
import common.util.bean.BeanUtil;
import common.util.idgn.IdGenerate;
import common.util.idgn.IdGenerateTargetTable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import common.util.bean.ApplicationContextProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * 현 프로젝트의 Global 자원 관련 Bean 설정 Class
 *
 * @author 서영석
 * @since 2023.10.24
 */
@Configuration
public class RootContextConfig {

    /**
     * Spring Application Context 객체 Bean 설정
     *
     * @author 서영석
     * @since 2023.10.24
     */
    @Bean(name = "applicationContextProvider")
    public ApplicationContextProvider getApplicationContextProvider () {
        ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        return applicationContextProvider;
    }

    /**
     * JSON Parser 라이브러리 Class Bean 설정
     *
     * @author 서영석
     * @since 2023.10.24
     */
    @Bean(name = "objectMapper")
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        //기본 날짜 포맷 비활성화
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.ALWAYS);

        //새로운 날짜 포맷 세팅
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return mapper;
    }

    /**
     * ID Generate Class Bean 설정
     *
     * @author 서영석
     * @since 2023.10.24
     */
    @Bean(name = "idGenerate")
    public IdGenerate getIdGenerate() {
        IdGenerate idGenerate = new IdGenerate();
        idGenerate.setDataSource((HikariDataSource) BeanUtil.getBean("mainHikariDataSource"));

        String[] tableNames = {"mold", "order_mold", "production", "cooperator"};
        ArrayList<IdGenerateTargetTable> idGenerateTargetTableList = new ArrayList<>(tableNames.length);
        for (String tableName : tableNames) {
            IdGenerateTargetTable targetTable = new IdGenerateTargetTable();
            targetTable.setTableName(tableName);
            targetTable.setPrefix(tableName.toUpperCase());
            targetTable.setLength((short) 10);
            idGenerateTargetTableList.add(targetTable);
        }
        idGenerate.setIdGenerateTargetTableList(idGenerateTargetTableList);
        idGenerate.idGenerateTableCreate();
        idGenerate.initIdGenerateTargetTableList();

        return idGenerate;
    }

}
