package com.jobintechtracking.app.DTO;

import lombok.Data;

@Data
public class ParcoursDTO {

    private long id;
    private String parcoursName;
    private String parcoursDescription;
    private Long stepsCount;
    private String imageUrl;
    private Long formationId;



    public Long getId (){
        return  id;
    }
    public  void setId(Long id){
        this.id=id;
    }

}
