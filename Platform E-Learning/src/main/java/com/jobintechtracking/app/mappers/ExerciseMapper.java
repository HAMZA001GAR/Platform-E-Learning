package com.jobintechtracking.app.mappers;

import com.jobintechtracking.app.DTO.DoingDTO;
import com.jobintechtracking.app.entities.Doing;
import com.jobintechtracking.app.entities.Steps;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper extends AbstractEntityDtoModelMapper <Doing, DoingDTO> {

    @Override
    public DoingDTO convertToDTO(Doing doing) {
        DoingDTO doingDTO = new DoingDTO ( );
        doingDTO.setId ( doing.getId ( ) );
        doingDTO.setTask ( doing.getTask ( ) );
        if (doing.getSteps ( ) != null) {
            doingDTO.setStepsId ( doing.getSteps ( ).getId ( ) );
        }
        return doingDTO;
    }

    @Override
    public Doing convertToEntity(DoingDTO doingDTO) {
        Doing doing = new Doing ( );
        doing.setId ( doingDTO.getId ( ) );
        doing.setTask ( doingDTO.getTask ( ) );
        if (doingDTO.getStepsId ( ) != null) {
            Steps steps = new Steps ( );
            steps.setId ( doingDTO.getStepsId ( ) );
            doing.setSteps ( steps );
        }
        return doing;
    }
}
