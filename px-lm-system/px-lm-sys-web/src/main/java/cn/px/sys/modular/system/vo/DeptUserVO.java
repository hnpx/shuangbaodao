package cn.px.sys.modular.system.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 部门及部门下用户实体类
 */
public class DeptUserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 部门
     */
    DeptVO dept;
    /**
     * 该部门下的用户
     */
    List<UserVO> users;
    /**
     * 该部门下的子部门
     */
    List<DeptUserVO> depts;

    public DeptVO getDept() {
        return dept;
    }

    public void setDept(DeptVO dept) {
        this.dept = dept;
    }

    public List<UserVO> getUsers() {
        return users;
    }

    public void setUsers(List<UserVO> users) {
        this.users = users;
    }

    public List<DeptUserVO> getDepts() {
        return depts;
    }

    public void setDepts(List<DeptUserVO> depts) {
        this.depts = depts;
    }
}
