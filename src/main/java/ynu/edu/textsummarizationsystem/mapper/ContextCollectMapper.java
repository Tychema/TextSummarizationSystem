package ynu.edu.textsummarizationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ynu.edu.textsummarizationsystem.beans.ContextCollect;

import java.util.List;

@Mapper
public interface ContextCollectMapper extends  BaseMapper<ContextCollect>{

    public List<ContextCollect> getContextCollectById(@Param("userid") String userid,
                                        @Param("page") Integer page);

    public List<ContextCollect> getAllContextCollect();


}
