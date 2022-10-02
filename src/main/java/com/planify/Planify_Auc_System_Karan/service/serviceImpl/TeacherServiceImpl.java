package com.planify.Planify_Auc_System_Karan.service.serviceImpl;

import com.planify.Planify_Auc_System_Karan.model.Teacher;
import com.planify.Planify_Auc_System_Karan.repository.TeacherRepository;
import com.planify.Planify_Auc_System_Karan.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("teacherServiceImpl")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher createNewTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }
}
