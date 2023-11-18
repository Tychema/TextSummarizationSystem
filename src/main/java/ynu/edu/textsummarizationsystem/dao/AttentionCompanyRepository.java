package ynu.edu.textsummarizationsystem.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ynu.edu.textsummarizationsystem.beans.AttentionCompany;


public interface AttentionCompanyRepository extends MongoRepository<AttentionCompany,Integer> {
}
