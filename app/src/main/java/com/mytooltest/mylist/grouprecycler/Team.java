package com.mytooltest.mylist.grouprecycler;

import java.util.List;

public class Team {
    public final String title;
    public final List<Member> members;

    public Team(String title, List<Member> members) {
        this.title = title;
        this.members = members;
    }
}
