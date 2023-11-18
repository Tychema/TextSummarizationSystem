package ynu.edu.textsummarizationsystem.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ynu.edu.textsummarizationsystem.beans.News;


public interface NewsRepository extends MongoRepository<News,Integer> {


}
