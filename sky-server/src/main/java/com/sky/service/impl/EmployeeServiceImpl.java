package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工信息
     *
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {
        // 保存员工信息
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDTO, employee);

        employee.setStatus(StatusConstant.ENABLE); // 默认启用状态

        String password = DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()); // 默认密码123456进行md5加密
        employee.setPassword(password); // 默认密码

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        employee.setCreateUser(BaseContext.getCurrentId()); // 设置创建人ID
//        employee.setUpdateUser(BaseContext.getCurrentId()); // 设置更新人ID

        employeeMapper.insert(employee);
    }

    /**
     * 分页查询员工信息
     *
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult getEmployeePage(EmployeePageQueryDTO employeePageQueryDTO) {
        // Begin pagination
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        // Query employee data
        Page<Employee> page = employeeMapper.getEmployeePage(employeePageQueryDTO);
        // Convert to PageResult
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        // Check is current employee's ID disabled
        Long currentEmployeeId = BaseContext.getCurrentId();
        if(currentEmployeeId.equals(id)) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        // Update employee status
//        employeeMapper.updateStatus(id, status, currentEmployeeId);
        Employee employee = Employee.builder()
                .id(id)
                .status(status).build();
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId()).build();
        employeeMapper.updateById(employee);
    }

    /**
     * 更新员工信息
     *
     * @param employeeDTO
     */
    public void updateEmployee(EmployeeDTO employeeDTO) {
        // Convert DTO to entity
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        // Set update time and user
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        // Update employee information in the database
       employeeMapper.updateById(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.selectById(id);

        return employee;
    }

}
