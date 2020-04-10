package com.java.waterFlowSensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.ChartDAO;
import com.java.waterFlowSensor.DTO.ChartDTO;

import lombok.extern.java.Log;

@Log
@Service
public class ChartService {
	
	@Autowired
	ChartDAO chartDAO;
	
	public ChartDTO getChart(String chartId, String username) {
		if(!chartDAO.existsByChartIdAndUsername(chartId, username)) {
			throw new IllegalArgumentException("Gráfico não encontrado");
		}
		
		ChartDTO chart = chartDAO.findByChartIdAndUsername(chartId, username);
		
		return chart;
	}

}
