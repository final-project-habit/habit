package com.habit;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.time.LocalDate;

@SpringBootApplication
public class HabitApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabitApplication.class, args);
    }

    // Mapper 객체 생성
    // dataSource -> application.properties 의 오라클 DB 환경설정이 담겨있음
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml");
        bean.setMapperLocations(res);
        return bean.getObject();
    }
    //apfhd
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }
}
