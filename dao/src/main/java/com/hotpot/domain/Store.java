package com.hotpot.domain;

public class Store {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column store.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column store.store_name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String storeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column store.address
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column store.phone
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column store.owner_id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private Integer ownerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column store.create_time
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    private Long createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column store.id
     *
     * @return the value of store.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column store.id
     *
     * @param id the value for store.id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column store.store_name
     *
     * @return the value of store.store_name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column store.store_name
     *
     * @param storeName the value for store.store_name
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column store.address
     *
     * @return the value of store.address
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column store.address
     *
     * @param address the value for store.address
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column store.phone
     *
     * @return the value of store.phone
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column store.phone
     *
     * @param phone the value for store.phone
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column store.owner_id
     *
     * @return the value of store.owner_id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column store.owner_id
     *
     * @param ownerId the value for store.owner_id
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column store.create_time
     *
     * @return the value of store.create_time
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column store.create_time
     *
     * @param createTime the value for store.create_time
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}