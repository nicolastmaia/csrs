package br.ufrrj.labweb.campussocial.model;

import java.util.List;

import org.springframework.lang.Nullable;

public class InterestRequestData {

    private List<String> nameList;

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<String> getNameList() {
        return nameList;
    }

}
