package repository.custom.impl;

import dto.User;
import entity.UserEntity;
import repository.custom.UserDao;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean save(UserEntity userEntity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(UserEntity userEntity) {
        return false;
    }

    @Override
    public UserEntity serach(String name) {
        return null;
    }

    @Override
    public String getLastID() {
        return "";
    }
}
