package br.ufrrj.labweb.campussocial.model;

import java.util.List;

import org.springframework.lang.Nullable;

public class InterestRequestData {

    @Nullable
    private List<String> nameList;

    @Nullable
    private List<Long> interestIdList;

    @Nullable
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

    public List<Long> getInterestIdList() {
        return interestIdList;
    }

    public void setInterestIdList(List<Long> interestIdList) {
        this.interestIdList = interestIdList;
    }

}
