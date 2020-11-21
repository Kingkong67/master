package spingboot.express.mapper;

/**
 * 用户模块的持久层接口
 */

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import spingboot.express.pojo.User;


@Repository
public interface UserMapper {

    /**
     * 获取用户信息(ByUserID)
     * @param telephone 传入userID
     * @return 返回用户信息
     */
    @Select("select * from user where telephone = #{telephone}")
    User getUserByTel(@Param("telephone") String telephone);

    /**
     * 获取用户信息(ByUserID)
     * @param userID 传入userID
     * @return 返回用户信息
     */
    @Select("select * from user where id = #{userID}")
    User getUserByUserID(@Param(value = "userID") Long userID);

    /**
     * 编辑用户信息
     * @param user 传入更改的用户信息
     * @return int 返回数据更新条数
     */
    @Update("update user set nickname = #{nickname}, password = #{password},telephone = #{telephone},\n" +
            "                        sex = #{sex}, name = #{name}, id_card = #{id_card},idCardImage = #{idCardImage},\n" +
            "                        stuCardImage = #{stuCardImage}, address = #{address}, create_time = #{create_time},\n" +
            "                        totalOrderCount = #{totalOrderCount}, successOrderCount = #{successOrderCount}, failOrderCount = #{failOrderCount}\n" +
            "                    where id = #{id}")
    int changeUserInfo(User user);

    /**
     * 注册用户
     * @param user 传入用户信息
     * @return 返回注册结果
     */
    @Insert("insert into user values (#{id}, #{nickname}, #{password}, #{telephone},\n" +
            "                                 #{sex}, #{name}, #{id_card}, #{idCardImage}, #{stuCardImage},\n" +
            "                                 #{address}, now(), #{totalOrderCount}, #{successOrderCount}, #{failOrderCount})")
    int addUser(User user);

    /**
     * 删除用户信息
     * @param userID 传入userID
     * @return 返回删除结果
     */
    @Delete("delete from user where id = #{userID}")
    int deleteUser(@Param(value = "userID") Long userID);

}
