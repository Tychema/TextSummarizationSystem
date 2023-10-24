package ynu.edu.textsummarizationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.edu.textsummarizationsystem.beans.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
