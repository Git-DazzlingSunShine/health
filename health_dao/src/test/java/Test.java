import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.mapper.UserMapper;
import com.yanglei.pojo.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class Test {

    @Reference
    private UserMapper userMapper;

    @org.junit.Test
    public void selectAllUserTest() {
        List<User> users = userMapper.selectAllUser();
        System.out.println("users = " + users);
    }

}
