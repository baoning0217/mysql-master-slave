package com.xishanqu.slave.service;

import com.xishanqu.slave.entity.Student;
import com.xishanqu.slave.mapper.StudentMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 17:25
 */
@Service
@Log4j2
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * @Desc 增加
     * @Author BaoNing
     * @Time 2020/3/27 17:26
     */
    public Integer insertStudent(Student student){
        return studentMapper.insertStudent(student);
    }


    /**
     * @Desc 查询
     * @Author BaoNing
     * @Time 2020/3/27 17:27
     */
    public Student queryStudentId(Student student){
        return studentMapper.queryStudentId(student);
    }


}
