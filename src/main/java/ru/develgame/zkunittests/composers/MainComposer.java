/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2021 Ilya Zemskov */

package ru.develgame.zkunittests.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class MainComposer extends SelectorComposer<Component> {
    @Wire private Textbox nameTextBox;

    @Listen("onClick = #helloButton")
    public void helloButtonOnClick() {
        if (nameTextBox.getText().isEmpty()) {
            Messagebox.show("Name cannot be empty",
                    null, 0,  Messagebox.EXCLAMATION);
            return;
        }

        Messagebox.show("Hello: " + nameTextBox.getText() + "!",
                null, 0,  Messagebox.INFORMATION);
    }
}
