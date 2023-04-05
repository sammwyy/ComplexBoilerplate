package com.sammwy.complexboilerplate.utils;

import com.sammwy.libchat.chat.Component;
import com.sammwy.libchat.chat.TextComponent;
import com.sammwy.libchat.events.ClickEvent;
import com.sammwy.libchat.events.ClickEventType;

public class ComponentUtils {
    public static Component createClickeableText(String text, String command) {
        return new TextComponent(text).setClickEvent(new ClickEvent(ClickEventType.RUN_COMMAND, command));
    }
}
