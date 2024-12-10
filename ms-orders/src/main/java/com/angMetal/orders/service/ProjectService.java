package com.angMetal.orders.service;

import com.angMetal.orders.entity.Projet;
import com.angMetal.orders.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Retrieve all reports
    public List<Projet> getAllProjects() {
        return projectRepository.findAll();
    }


}
