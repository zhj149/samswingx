package org.sam.swing;

import java.util.Date;

/**
 * 测试用的实体类型
 * @author sam
 *
 */
public class TestEntity {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 生日
	 */
	private Date birthday;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 所属部门
	 */
	private DeptEntity dept;
	
	/**
	 * 婚姻状况
	 */
	private Integer marryState;
	
	/**
	 * 最后登入时间
	 */
	private Date lastLogin;
	
	/**
	 * 是否在职
	 */
	private Boolean onDuty;
	
	/**
	 * 角色
	 */
	private Integer role;
	
	/**
	 * 颜色
	 */
	private Integer color;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 主键
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 主键
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 代码
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 代码
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public DeptEntity getDept() {
		return dept;
	}

	public void setDept(DeptEntity dept) {
		this.dept = dept;
	}

	public Integer getMarryState() {
		return marryState;
	}

	public void setMarryState(Integer marryState) {
		this.marryState = marryState;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(Boolean onDuty) {
		this.onDuty = onDuty;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	 
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * 颜色
	 * @return
	 */
	public Integer getColor() {
		return color;
	}

	/**
	 * 颜色
	 * @param color
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return "id=" + id + " code=" + code + " name=" + name;
	}
	
}
