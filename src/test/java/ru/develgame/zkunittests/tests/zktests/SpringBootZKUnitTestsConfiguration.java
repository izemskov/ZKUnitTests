/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2021 Ilya Zemskov */

package ru.develgame.zkunittests.tests.zktests;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.develgame.zkunittests.composers.MainComposer;
import ru.develgame.zkunittests.tests.common.ZKBeanPostProcessor;

@TestConfiguration
@ComponentScan("ru.develgame.zkunittests.tests.common")
public class SpringBootZKUnitTestsConfiguration {
    @Bean
    public ZKBeanPostProcessor zkBeanPostProcessor() {
        return new ZKBeanPostProcessor();
    }

    @Bean
    public MainComposer mainComposer() {
        return new MainComposer();
    }
}
