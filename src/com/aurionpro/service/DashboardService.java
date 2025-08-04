package com.aurionpro.service;

import java.sql.SQLException;
import java.util.List;

import com.aurionpro.dao.DashboardDao;
import com.aurionpro.model.Dashboard;

public class DashboardService {
    private DashboardDao dashboardDao;

    public DashboardService(DashboardDao dao) {
        this.dashboardDao = dao;
    }

    public List<Dashboard> getDashboardData() throws SQLException {
        return dashboardDao.fetchDashboardData();
    }
}
