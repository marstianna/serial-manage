package com.hotpot.domain;

public class Staff {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff.name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff.mobilephone
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String mobilephone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff.identity
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String identity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff.email
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff.staff_level
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private Byte staffLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column staff.create_time
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private Integer createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column staff.id
     *
     * @return the value of staff.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column staff.id
     *
     * @param id the value for staff.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column staff.name
     *
     * @return the value of staff.name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column staff.name
     *
     * @param name the value for staff.name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column staff.mobilephone
     *
     * @return the value of staff.mobilephone
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column staff.mobilephone
     *
     * @param mobilephone the value for staff.mobilephone
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column staff.identity
     *
     * @return the value of staff.identity
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column staff.identity
     *
     * @param identity the value for staff.identity
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column staff.email
     *
     * @return the value of staff.email
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column staff.email
     *
     * @param email the value for staff.email
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column staff.staff_level
     *
     * @return the value of staff.staff_level
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public Byte getStaffLevel() {
        return staffLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column staff.staff_level
     *
     * @param staffLevel the value for staff.staff_level
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setStaffLevel(Byte staffLevel) {
        this.staffLevel = staffLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column staff.create_time
     *
     * @return the value of staff.create_time
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column staff.create_time
     *
     * @param createTime the value for staff.create_time
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}