package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        List<Category>categories = new ArrayList<>();

        String query = "SELECT * FROM categories";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rows = statement.executeQuery();

            while (rows.next()){
                categories.add(new Category(
                        rows.getInt(1),
                        rows.getString(2),
                        rows.getString(3)
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getById(int categoryId){
     String query = "SELECT * FROM categories WHERE category_id = ?";
    try (Connection conn = dataSource.getConnection();
    PreparedStatement stmt = conn.prepareStatement(query)){

        stmt.setInt(1,categoryId);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()){
            return new Category(
                    resultSet.getInt("category_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description")
            );
        }
    }catch (SQLException e) {
    e.printStackTrace();
}
        return null;
    }

    @Override
    public Category create(Category category)
    {
        Category createdCategory = null;
        String query = "INSERT INTO Categories( name, description) VALUES (?,?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){

            preparedStatement.setString(1,category.getName());
            preparedStatement.setString(2, category.getDescription());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                createdCategory = getById(resultSet.getInt(1));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        String query = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, categoryId);

            stmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int categoryId)
    {
        String query = "DELETE FROM Categories WHERE category_id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){

            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
