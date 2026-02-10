package com.programmingtechie.tasklist.repository.impl;

import com.programmingtechie.tasklist.domain.exception.ResurceMappingException;
import com.programmingtechie.tasklist.domain.task.Task;
import com.programmingtechie.tasklist.repository.DataSoruceConfig;
import com.programmingtechie.tasklist.repository.TaskRepository;
import com.programmingtechie.tasklist.repository.mappers.TaskRowMapper;
import com.programmingtechie.tasklist.web.dto.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.data.relational.core.sql.Update;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
//@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final DataSoruceConfig dataSoruceConfig;

    private final String FIND_BY_ID = """
             SELECT t.id task_id
            ,t.title task_title
            ,t.description task_description
            ,t.status task_status
            ,t.expiration_date task_expiration_date
            FROM tasklist.tasks t where t.id=?""";

    private final String Find_All_By_User_Id= """
            SELECT t.id task_id
            ,t.title task_title
            ,t.description task_description
            ,t.status task_status
            ,t.expiration_date task_expiration_date
            FROM tasklist.tasks t
            join tasklist.users_task ut on t.id = ut.task_id
            where ut.user_id=?
            """;
    private final String ASSIGN= """
            INSERT INTO tasklist.users_task(task_id,user_id)VALUES (?, ?)""";
    private final String CREATE= """
            INSERT INTO tasklist.tasks(title,description,status,expiration_date) VALUES(?,?,?,?)""";
    private final String UPDATE="""
   UPDATE tasklist.tasks
   SET title=?,description=?,status=?,expiration_date=? where id=?""";

private final String DELETE= """
        DELETE FROM tasklist.tasks where id=?""";
    @Override
    public Optional<Task> findById(Long id) {
        try{
            Connection connection = dataSoruceConfig.getConnection();
            PreparedStatement statement=connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1,id);
            try(ResultSet rs=statement.executeQuery()){
                return Optional.ofNullable(TaskRowMapper.mapRow(rs));
            }
        }catch (SQLException ex){
            throw new ResurceMappingException("Erorr while finding user by id");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try{
            Connection connection = dataSoruceConfig.getConnection();
            PreparedStatement statement=connection.prepareStatement(Find_All_By_User_Id);
            statement.setLong(1,userId);
            try(ResultSet rs=statement.executeQuery()){
                return TaskRowMapper.mapRows(rs);
            }
        }catch (SQLException ex){
            throw new ResurceMappingException("Erorr while finding all by user id");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try{
            Connection connection = dataSoruceConfig.getConnection();
            PreparedStatement statement=connection.prepareStatement(ASSIGN);
            statement.setLong(1,taskId);
            statement.setLong(2,userId);
            statement.executeUpdate();

        }catch (SQLException ex){
            throw new ResurceMappingException("Erorr while assigning to user");
        }
    }

    @Override
    public void update(Task task) {
        try{
            Connection connection = dataSoruceConfig.getConnection();
            PreparedStatement statement=connection.prepareStatement(UPDATE);
            statement.setString(1,task.getTitle());
            if(task.getDescription()==null){
                statement.setNull(2, Types.VARCHAR);
            }else {
                statement.setString(2, task.getDescription());
            }
            if(task.getExpirationDate()==null){
                statement.setNull(3, Types.TIMESTAMP);
                            }else {
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            }
            statement.setString(4,task.getStatus().name());
            statement.setLong(5,task.getId());
            statement.executeUpdate();

        }catch (SQLException ex){
            throw new ResurceMappingException("Erorr while updatening user");
        }
    }

    @Override
    public void create(Task task) {
        try{
            Connection connection = dataSoruceConfig.getConnection();
            PreparedStatement statement=connection.prepareStatement(CREATE,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1,task.getTitle());
            if(task.getDescription()==null){
                statement.setNull(2, Types.VARCHAR);
            }else {
                statement.setString(2, task.getDescription());
            }
            statement.setString(3,task.getStatus().name());

            if(task.getExpirationDate()==null){
                statement.setNull(4, Types.TIMESTAMP);
            }else {
                statement.setTimestamp(4, Timestamp.valueOf(task.getExpirationDate()));
            }
          //  statement.executeUpdate();
            int rowsInserted = statement.executeUpdate();
            System.out.println("Inserted rows: " + rowsInserted);
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                task.setId(rs.getLong(1));
            }

        }catch (SQLException ex){
            ex.printStackTrace();
            throw new ResurceMappingException("Erorr while createning task");
        }
    }

    @Override
    public void delete(Long id) {
        try{
            Connection connection = dataSoruceConfig.getConnection();
            PreparedStatement statement=connection.prepareStatement(DELETE);
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw new ResurceMappingException("Erorr while deleting task");
        }
    }
}
