package ynu.edu.textsummarizationsystem.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ynu.edu.textsummarizationsystem.beans.HistoryRecords;

public interface HistoryRecordsRepository extends MongoRepository<HistoryRecords,Integer> {
}
