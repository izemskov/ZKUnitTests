/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2021 Ilya Zemskov */

package ru.develgame.zkunittests.tests.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ZKBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<>();

    @Autowired
    private ApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof SelectorComposer) {
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            for (Field elem : declaredFields) {
                if (elem.isAnnotationPresent(WireVariable.class)) {
                    elem.setAccessible(true);
                    try {
                        elem.set(bean, context.getBean(elem.getType()));
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return bean;
    }
}
