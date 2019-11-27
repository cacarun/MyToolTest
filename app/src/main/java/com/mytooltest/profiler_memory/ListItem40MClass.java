package com.mytooltest.profiler_memory;

public class ListItem40MClass {

    byte[] content = new byte[1000 * 1000 * 40];

    ListItem40MClass() {
        for (int i = 0; i < content.length; i++) {
            content[i] = 1;
        }
    }

    ListItem40MClass next;
}
