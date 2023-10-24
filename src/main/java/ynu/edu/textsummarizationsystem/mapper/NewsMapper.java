package ynu.edu.textsummarizationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.edu.textsummarizationsystem.beans.News;

@Mapper
public interface NewsMapper extends BaseMapper<News> {
}
