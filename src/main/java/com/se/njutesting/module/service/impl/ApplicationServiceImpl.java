package com.se.njutesting.module.service.impl;

import com.se.njutesting.module.entity.Application;
import com.se.njutesting.module.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Application> selectAppByName(String name) {
        Query query  = new Query();
        query.addCriteria( new Criteria("software_name").is(name));
        return mongoTemplate.find(query, Application.class, "application");
    }

    @Override
    public void insert(Application app) {
        mongoTemplate.insert(app, "application");
    }

}
