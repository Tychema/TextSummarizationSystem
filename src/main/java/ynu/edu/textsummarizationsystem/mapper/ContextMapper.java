package ynu.edu.textsummarizationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ynu.edu.textsummarizationsystem.beans.Context;

import java.util.List;

@Mapper
public interface ContextMapper extends BaseMapper<Context> {
    public List<Context> getContextById(@Param("userid") String userid,
                                    @Param("page") Integer page);

    public List<Context> getAllContext();


}
