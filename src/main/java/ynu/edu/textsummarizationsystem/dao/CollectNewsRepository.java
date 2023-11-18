package ynu.edu.textsummarizationsystem.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ynu.edu.textsummarizationsystem.beans.CollectNews;

public interface CollectNewsRepository extends MongoRepository<CollectNews,Integer> {
}
