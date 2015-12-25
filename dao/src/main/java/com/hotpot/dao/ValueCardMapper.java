package com.hotpot.dao;


import com.hotpot.domain.ValueCard;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueCardMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table value_card
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table value_card
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    int insert(ValueCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table value_card
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    int insertSelective(ValueCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table value_card
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    ValueCard selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table value_card
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    int updateByPrimaryKeySelective(ValueCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table value_card
     *
     * @mbggenerated Fri Dec 25 15:10:08 CST 2015
     */
    int updateByPrimaryKey(ValueCard record);
}