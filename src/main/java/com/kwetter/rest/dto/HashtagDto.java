package com.kwetter.rest.dto;

/**
 * Created by hein on 5/21/17.
 */
public class HashtagDto {

    private Long id;
    private String tag;
    private Long occurrences;

    public HashtagDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Long occurrences) {
        this.occurrences = occurrences;
    }
}
