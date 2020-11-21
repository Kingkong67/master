package spingboot.express.service;

/**
 * 用户模块的业务层接口
 */


import spingboot.express.pojo.User;

import java.util.HashMap;
import java.util.Map;

public interface UserService {

    User loginUserWithPwd(HashMap<String, Object> paramMap) throws Exception;

    int addUser(HashMap<String, Object> paramMap) throws Exception;

    User loginWithCode(HashMap<String, Object> paramMap) throws Exception;

    int deleteUser(HashMap<String, Object> paramMap) throws Exception;

    Map<String, Object> getBasicUser(HashMap<String, Object> paramMap) throws Exception;

    void realUser(HashMap<String, Object> paramMap) throws Exception;

    String getCode(HashMap<String, Object> paramMap) throws Exception;

    User viewIfFullUserInformation(HashMap<String, Object> paramMap) throws Exception;

    int resetPwd(HashMap<String, Object> paramMap) throws Exception;

    User getAllInfo(HashMap<String, Object> paramMap) throws Exception;

    String getAddress(HashMap<String, Object> paramMap) throws Exception;


}

