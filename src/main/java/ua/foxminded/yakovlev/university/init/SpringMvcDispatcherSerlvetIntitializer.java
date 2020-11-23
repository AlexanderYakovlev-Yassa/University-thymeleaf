package ua.foxminded.yakovlev.university.init;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    
    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
          FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("ncodingFilter", new CharacterEncodingFilter("UTF-8", true));
          encodingFilter.addMappingForUrlPatterns(null, false, "/*");

        super.onStartup(servletContext);
    }
}