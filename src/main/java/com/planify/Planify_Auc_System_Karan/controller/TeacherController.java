package com.planify.Planify_Auc_System_Karan.controller;

import com.planify.Planify_Auc_System_Karan.dto.ResponseDTO;
import com.planify.Planify_Auc_System_Karan.model.Teacher;
import com.planify.Planify_Auc_System_Karan.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    @Qualifier("teacherServiceImpl")
    private TeacherService teacherService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> saveTeacher(@RequestBody Teacher teacher){
        final Teacher returnTeacher = teacherService.createNewTeacher(teacher);
        ResponseDTO responseDTO = new ResponseDTO("SUCCESSFULL.", "SUCCESS", returnTeacher);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
