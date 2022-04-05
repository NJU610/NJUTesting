package com.se.njutesting.module.service;

import com.se.njutesting.module.entity.Application;

import java.util.List;

public interface ApplicationService {

    List<Application> selectAppByName(String name);
    void insert(Application app);

}
