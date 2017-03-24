package de.mh.lists.entity.repository;

import de.mh.lists.entity.TaskList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TaskListRepository extends CrudRepository<TaskList, Long> {

    List<TaskList> findByUserId(long userId);

    TaskList findByUserIdAndName(long userId, String name);
}
