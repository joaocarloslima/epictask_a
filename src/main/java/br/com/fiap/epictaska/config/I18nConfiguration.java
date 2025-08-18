package br.com.fiap.epictaska.config;

import org.hibernate.Session;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import java.util.Locale;

@Configuration
public class I18nConfiguration implements WebMvcConfigurer {

    @Bean
    MessageSource messageSource(){
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Bean
    LocaleResolver localeResolver(){
        var localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("pt", "BR"));
        return localeResolver;
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor(){
        var changeInterceptor = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("lang");
        return changeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
