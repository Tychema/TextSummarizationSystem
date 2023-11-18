package ynu.edu.textsummarizationsystem.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ynu.edu.textsummarizationsystem.beans.counters;

public interface CountersRepository extends MongoRepository<counters,String>{
}
