/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2021 Ilya Zemskov */

package ru.develgame.zkunittests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZKUnitTestsApp {
    public static void main(String[] args) {
        SpringApplication.run(ZKUnitTestsApp.class, args);
    }
}
