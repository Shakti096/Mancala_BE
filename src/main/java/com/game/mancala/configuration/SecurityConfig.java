package com.game.mancala.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

/**
 * @author Shakti Shekhawat
 * @version 1.0
 * configuration specific for the security.
 * ============================================================
 * This enable the connection between cross domain ports.
 * User can connect with angular url with port 4200 to springboot url with port 9080
 * On server static content direct accessible as this run on same jvm , without any cross domain connectivity.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	 httpSecurity.csrf().disable();
    	 httpSecurity.addFilterBefore(new com.game.mancala.configuration.CorsFilter(), ChannelProcessingFilter.class);
    }    
}
