package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Order;
import com.demo.model.OrdermemberView;
import com.demo.repository.OrderRepository;

@RestController
public class OrderRestController {

	@Autowired
	OrderRepository orderRepo;

	// 會員查詢自己的訂單 (member.js 使用)
	@PostMapping("/order/select")
	public List<Order> orderList(@RequestParam String memberid) {
		return orderRepo.findByMemberId(memberid);
	}

	// 員工查詢訂單 (複雜搜尋)
	@GetMapping("/searchorder")
	public List<OrdermemberView> searchOrder(@RequestParam(required = false) String member_no,
			@RequestParam(required = false) String member_name, @RequestParam(required = false) String member_idnumber,
			@RequestParam(required = false) String member_phone, @RequestParam(required = false) String pickup_location,
			@RequestParam(required = false) String pickup_date_start,
			@RequestParam(required = false) String pickup_date_end, @RequestParam(required = false) String status) {

		// 空字串處理
		member_no = (member_no != null && member_no.isEmpty()) ? null : member_no;
		member_name = (member_name != null && member_name.isEmpty()) ? null : member_name;
		member_idnumber = (member_idnumber != null && member_idnumber.isEmpty()) ? null : member_idnumber;
		member_phone = (member_phone != null && member_phone.isEmpty()) ? null : member_phone;
		pickup_location = (pickup_location != null && pickup_location.isEmpty()) ? null : pickup_location;
		pickup_date_start = (pickup_date_start != null && pickup_date_start.isEmpty()) ? null : pickup_date_start;
		pickup_date_end = (pickup_date_end != null && pickup_date_end.isEmpty()) ? null : pickup_date_end;
		status = (status != null && status.isEmpty()) ? null : status;

		return orderRepo.searchOrder(member_no, member_name, member_idnumber, member_phone, pickup_location,
				pickup_date_start, pickup_date_end, status);
	}
}