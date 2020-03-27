package com.xishanqu.slave.controller;

import com.xishanqu.slave.entity.Student;
import com.xishanqu.slave.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 17:28
 */
@RestController
@RequestMapping("/api/v1/admin/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    /**
     * @Desc 新增
     * @Param
     * @Return
     * @Author BaoNing
     * @Time 2020/3/27 17:30
     */
    @PostMapping("/insert")
    public Integer insertStudent(@RequestBody Student student){
        return studentService.insertStudent(student);
    }


    /**
     * @Desc 查询
     * @Param
     * @Return
     * @Author BaoNing
     * @Time 2020/3/27 17:30
     */
    @PostMapping("/queryStudentId")
    public Student queryStudentId(@RequestBody Student student){
        return studentService.queryStudentId(student);
    }


}
