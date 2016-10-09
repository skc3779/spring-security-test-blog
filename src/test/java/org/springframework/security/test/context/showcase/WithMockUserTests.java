/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.security.test.context.showcase;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.test.context.showcase.service.HelloMessageService;
import org.springframework.security.test.context.showcase.service.MessageService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Rob Winch
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class WithMockUserTests {
    @Autowired
    private MessageService messageService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        messageService.getMessage();
    }

    @Test
    @WithMockUser
    public void getMessageWithMockUser() {
        String message = messageService.getMessage();

        assertThat(message).contains("user");
    }

    @Test
    @WithMockUser("customUsername")
    public void getMessageWithMockUserCustomUsername() {
        String message = messageService.getMessage();
        assertThat(message).contains("customUsername");
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void getMessageWithMockUserCustomUser() {
        String message = messageService.getMessage();
        assertThat(message).contains("admin").contains("ROLE_USER").contains("ROLE_ADMIN");
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @ComponentScan(basePackageClasses = HelloMessageService.class)
    static class Config {
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            auth
//                .inMemoryAuthentication()
//                    .withUser("user").password("password").roles("USER");
        }
    }

}