package de.mh.lists.entity.repository;

import de.mh.lists.entity.Task;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByTaskListId(long taskListId);
}
