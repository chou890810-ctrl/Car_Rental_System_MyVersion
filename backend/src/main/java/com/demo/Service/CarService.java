package com.demo.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Car;
import com.demo.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	CarRepository repo;

	public List<Car> getAllCars() {
		return repo.findAll();
	}

	public Car findById(Integer id) {

		return repo.findById(id).orElse(null);
	}

	public List<Car> findBySeats(int seats) {

		return repo.findBySeats(seats);
	}

	public List<Car> findBySeatsIn(List<Integer> seatsList) {

		return repo.findBySeatsIn(seatsList);
	}
	
	public int calculateRentalDays(String dateRange) {
	    int rentalDays = 1;

	    try {
	        String[] range = dateRange.split(" - ");
	        LocalDate start = LocalDate.parse(range[0]);
	        LocalDate end = LocalDate.parse(range[1]);

	        rentalDays = (int) ChronoUnit.DAYS.between(start, end);

	        if (rentalDays <= 0) {
	            rentalDays = 1;
	        }
	    } catch (Exception e) {
	        // 解析失敗預設 1 天
	    }

	    return rentalDays;
	}

	
	private static final Map<String, List<Integer>> CAR_TYPE_MAP = Map.of(
		    "五人座", List.of(5),
		    "七九人座", List.of(7, 8, 9)
		);
	
	
	public List<Car> findCarsByType(String carType) {
	    List<Integer> seats = CAR_TYPE_MAP.get(carType);

	    if (seats == null) { 
	        return List.of(); // carType 無效 → 回傳空清單
	    }

	    return repo.findBySeatsIn(seats);
	}
}
