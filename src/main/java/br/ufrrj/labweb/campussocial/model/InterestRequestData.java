package br.ufrrj.labweb.campussocial.model;

import java.util.List;

import org.springframework.lang.NonNull;

public class InterestRequestData {

    @NonNull
    private List<String> nameList;
    private List<Long> postIdList;

    public void setPostIdList(List<Long> postIdList) {
        this.postIdList = postIdList;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<Long> getPostIdList() {
        return postIdList;
    }

}
