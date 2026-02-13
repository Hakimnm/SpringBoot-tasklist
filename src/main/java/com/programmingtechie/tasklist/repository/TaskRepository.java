package com.programmingtechie.tasklist.repository;

import com.programmingtechie.tasklist.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
select * from task t 
join users_task ut on t.id = ut.task_id
where ut.user_id = :user_id
       """,nativeQuery = true)
    List<Task> findAllByUserId(@Param("userId") Long userId);

}
