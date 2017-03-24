package de.mh.walter.entity.repository;

import de.mh.walter.entity.Task;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByTaskListId(long taskListId);
}
