package demo.dao;

import demo.entity.MUTModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MUTModelDao extends JpaRepository<MUTModel, Integer> {

    @Query(value = "select * from mut_model", nativeQuery = true)
    List<MUTModel> getMUTModelList();
////
////    List<Integer> getAllMUTIDList();

}
