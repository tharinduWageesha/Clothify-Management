package repository;

public interface CrudDao <T> extends SuperDao{
    boolean save(T t);
    boolean delete(String id);
    boolean update(T t);
    T serach(String name);
    String getLastID();
}
