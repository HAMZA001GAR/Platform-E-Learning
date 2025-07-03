package com.jobintechtracking.app.DTO;

import lombok.Data;

@Data
public class FormationsDTO {

    private Long id;
    private String formationsName;
    private String formationsDescription;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
