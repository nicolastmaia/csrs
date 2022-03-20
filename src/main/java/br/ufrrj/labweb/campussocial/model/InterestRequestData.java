package br.ufrrj.labweb.campussocial.model;

import java.util.List;

import org.springframework.lang.Nullable;

public class InterestRequestData {

    @Nullable
    private String name;

    @Nullable
    private List<String> nameList;

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getNameList() {
        return nameList;
    }

}
