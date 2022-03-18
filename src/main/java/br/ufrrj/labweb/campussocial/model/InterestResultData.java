package br.ufrrj.labweb.campussocial.model;

public class InterestResultData {

    private String id;
    private long post_id;
    private long interest_id;
    private String interest_name;

    public InterestResultData(String id, long post_id, long interest_id, String interest_name) {
        this.id = id;
        this.post_id = post_id;
        this.interest_id = interest_id;
        this.interest_name = interest_name;
    }

    public InterestResultData(String id, String interest_name) {
        this.id = id;
        this.interest_name = interest_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public long getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(long interest_id) {
        this.interest_id = interest_id;
    }

    public String getInterest_name() {
        return interest_name;
    }

    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }

}
