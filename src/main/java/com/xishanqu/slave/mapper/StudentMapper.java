package com.xishanqu.slave.mapper;

import com.xishanqu.slave.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 16:28
 */
@Mapper
public interface StudentMapper {

    /**
     * @Desc 新增
     */
    @Insert("INSERT INTO t_student(name,sex) VALUES (#{name},#{sex})")
    public Integer insertStudent(Student student);

    /**
     * @Desc 查询
     */
    @Select("SELECT * FROM t_student WHERE id = #{id}")
    public Student queryStudentId(Student student);

}
