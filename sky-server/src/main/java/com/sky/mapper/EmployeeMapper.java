package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工信息
     * @param employee
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user)" +
            "VALUE (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 分页查询员工信息
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> getEmployeePage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * Select an employee by ID
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee selectById(Long id);

    /**
     * Update the status of an employee
     * @param id
     * @param status
     */
    @Update("update employee set status = #{status}, update_time = now(), update_user = #{updateUser} where id = #{id}")
    void updateStatus(Long id, Integer status, Long updateUser);

    /**
     * Update employee information
     * @param employee
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateById(Employee employee);
}
