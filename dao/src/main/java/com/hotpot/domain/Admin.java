package com.hotpot.domain;

public class Admin {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.login_name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String loginName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.login_password
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String loginPassword;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin.id
     *
     * @return the value of admin.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin.id
     *
     * @param id the value for admin.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin.login_name
     *
     * @return the value of admin.login_name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin.login_name
     *
     * @param loginName the value for admin.login_name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin.login_password
     *
     * @return the value of admin.login_password
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin.login_password
     *
     * @param loginPassword the value for admin.login_password
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }
}