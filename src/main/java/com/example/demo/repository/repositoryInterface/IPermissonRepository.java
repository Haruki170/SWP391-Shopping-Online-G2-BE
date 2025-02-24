package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Permisson;

import java.util.List;

public interface IPermissonRepository {
    public List<Permisson> findAllPermissonsByRole(int roleid);
}
