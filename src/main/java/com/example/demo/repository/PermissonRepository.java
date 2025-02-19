package com.example.demo.repository;

import com.example.demo.entity.Permisson;
import com.example.demo.repository.repositoryInterface.IPermissonRepository;

import java.util.List;

public class PermissonRepository extends AbstractRepository<Permisson> implements IPermissonRepository {
    @Override
    public List<Permisson> findAllPermissonsByRole(int roleid) {
        return List.of();
    }
}
