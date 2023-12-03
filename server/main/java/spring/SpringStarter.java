package spring;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * SpringFramework Context 구동 Class
 *
 * @author 서영석
 * @since 2022.08.31
 */
public class SpringStarter implements WebApplicationInitializer {

    /**
     * SpringFramework Context 구동 시, 동작하는 이벤트 기능 (web.xml의 기능을 대체함)
     *
     * @author 서영석
     * @since 2022.08.31
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(
                spring.config.RootContextConfig.class,//Root Context
                spring.config.DataSourceContextConfig.class,//DataSource Context
                spring.config.MybatisContextConfig.class//Mybatis Context
        );
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // Web(Servlet) Context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(spring.config.servlet.WebContextConfig.class);

        // Dispatcher Servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // Filter
        FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        filter.setInitParameter("encoding","utf-8");
        filter.addMappingForServletNames(null,false,"dispatcher");
    }
}