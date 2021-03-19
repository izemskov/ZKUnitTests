/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2021 Ilya Zemskov */

package ru.develgame.zkunittests.tests.zktests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Messagebox;
import ru.develgame.zkunittests.composers.MainComposer;
import ru.develgame.zkunittests.tests.common.MockZKComponents;

@Import(SpringBootZKUnitTestsConfiguration.class)
@PrepareForTest({EventQueues.class, Messagebox.class, Executions.class})
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class SimpleTests {
    @Autowired
    private MainComposer mainComposer;

    @Autowired
    private MockZKComponents mockZKComponents;

    @Test
    public void simpleTest() throws NoSuchFieldException, IllegalAccessException {
        String value = "username";

        mockZKComponents.mockTextBox("nameTextBox", value, mainComposer);
        mockZKComponents.mockMessageBox();
        try {
            mainComposer.helloButtonOnClick();
        }
        catch (MockZKComponents.ZKTestException ex) {
            Assert.assertEquals(ex.getMessage(), "Hello: " + value + "!");
        }
    }

    @Test
    public void emptyName() throws NoSuchFieldException, IllegalAccessException {
        String value = "";

        mockZKComponents.mockTextBox("nameTextBox", value, mainComposer);
        mockZKComponents.mockMessageBox();
        try {
            mainComposer.helloButtonOnClick();
        }
        catch (MockZKComponents.ZKTestException ex) {
            Assert.assertEquals(ex.getMessage(), "Name cannot be empty");
        }
    }
}
