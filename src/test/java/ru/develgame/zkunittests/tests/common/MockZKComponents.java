/* This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Copyright 2021 Ilya Zemskov */

package ru.develgame.zkunittests.tests.common;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@Component
public class MockZKComponents {
    public void mockTextBox(String textBoxName, String value, SelectorComposer composer) throws NoSuchFieldException, IllegalAccessException {
        Textbox textbox = Mockito.mock(Textbox.class);
        when(textbox.getText()).thenReturn(value);

        Field textBoxField = composer.getClass().getDeclaredField(textBoxName);
        textBoxField.setAccessible(true);
        textBoxField.set(composer, textbox);
    }

    public void mockComboBox(String comboBoxName, int selectedIndex, SelectorComposer composer) throws NoSuchFieldException, IllegalAccessException {
        Combobox combobox = Mockito.mock(Combobox.class);
        when(combobox.getSelectedIndex()).thenReturn(selectedIndex);

        Field comboBoxField = composer.getClass().getDeclaredField(comboBoxName);
        comboBoxField.setAccessible(true);
        comboBoxField.set(composer, combobox);
    }

    public void mockWindow(String windowName, SelectorComposer composer) throws NoSuchFieldException, IllegalAccessException {
        Window window = Mockito.mock(Window.class);

        Field windowField = composer.getClass().getDeclaredField(windowName);
        windowField.setAccessible(true);
        windowField.set(composer, window);
    }

    public void mockQueue(Function<Void, Void> action) {
        PowerMockito.mockStatic(EventQueues.class);
        Mockito.when(EventQueues.lookup(anyString(), anyString(), anyBoolean())).thenAnswer(new Answer<EventQueue<Event>>() {
            @Override
            public EventQueue<Event> answer(InvocationOnMock invocationOnMock) throws Throwable {
                EventQueue<Event> eventQueue = Mockito.mock(EventQueue.class);

                doAnswer((Answer<Object>) invocationOnMock1 -> {
                    if (action != null)
                        action.apply(null);
                    return null;
                }).when(eventQueue).publish(any(Event.class));

                return eventQueue;
            }
        });
    }

    public void mockParams(Map<String, String> params) {
        PowerMockito.mockStatic(Executions.class);
        Mockito.when(Executions.getCurrent()).thenAnswer(new Answer<Execution>() {
            @Override
            public Execution answer(InvocationOnMock invocationOnMock) throws Throwable {
                Execution execution = Mockito.mock(Execution.class);

                when(execution.getArg()).thenAnswer(new Answer<Map<?,?>>() {
                    @Override
                    public Map<?, ?> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return params;
                    }
                });

                return execution;
            }
        });
    }

    public void mockMessageBox() {
        PowerMockito.mockStatic(Messagebox.class);
        Mockito.when(Messagebox.show(anyString(), any(), anyInt(), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                throw new ZKTestException(invocationOnMock.getArgument(0));
            }
        });
    }

    public void mockListModelList(String modelName, String value, SelectorComposer composer) throws NoSuchFieldException, IllegalAccessException {
        Field modelField = composer.getClass().getDeclaredField(modelName);
        modelField.setAccessible(true);

        ListModelList<String> list = new ListModelList<>(Arrays.asList(value));
        list.setSelection(Arrays.asList(value));

        modelField.set(composer, list);
    }

    public static class ZKTestException extends RuntimeException {
        public ZKTestException(String message) {
            super(message);
        }
    }
}
