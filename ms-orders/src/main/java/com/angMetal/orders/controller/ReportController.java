package com.angMetal.orders.controller;

import com.angMetal.orders.entity.Report;
import com.angMetal.orders.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<Report> getAllReport(){
        return reportService.getAllReports();
    }

}
