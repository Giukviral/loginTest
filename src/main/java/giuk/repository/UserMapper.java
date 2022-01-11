package giuk.repository;

import org.apache.ibatis.annotaions.*;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;

@Mapper
@Repository
public interface UserMapper {

    ArrayList<HashMap<String, Object>> findAll();
}