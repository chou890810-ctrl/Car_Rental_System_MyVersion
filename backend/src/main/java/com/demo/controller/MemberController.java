package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Member;
import com.demo.repository.MemberRepository;
import com.demo.util.JwtUtil;

@RestController
public class MemberController {

	@Autowired
	MemberRepository memRepo;

	@Autowired
	JwtUtil jwtUtil;

	// 註冊會員
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Member member) {
		// 產生下一個 member_id (例如 m00005)
		String memberid = memRepo.queryNextMemberId();
		int num = 0;
		if (memberid != null) {
			num = Integer.parseInt(memberid.substring(1));
		}
		String nextMember_id = String.format("m%05d", num + 1);
		member.setMemberId(nextMember_id);

		// 檢查唯一性
		if (memRepo.findByEmail(member.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email 已註冊");
		} else if (memRepo.findByIdNumber(member.getIdNumber()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("身分證字號已註冊");
		} else if (memRepo.findByPhone(member.getPhone()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("手機已註冊");
		} else {
			Member mem = memRepo.save(member);
			return ResponseEntity.ok(mem);
		}
	}

	// 會員登入
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		// 支援 身分證 或 Email 登入
		Member mem1 = memRepo.findByIdNumberAndPassword(username, password);
		Member mem2 = memRepo.findByEmailAndPassword(username, password);

		Member member = mem1 != null ? mem1 : mem2;

		if (member != null) {
			// 產生 JWT
			String token = jwtUtil.generateToken(member.getIdNumber());

			Map<String, Object> result = new HashMap<>();
			result.put("token", token);
			result.put("member", member); // 回傳會員資料供前端存 SessionStorage

			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(401).body("帳號或密碼錯誤");
		}
	}

	// 驗證 Token (前端 member.js 的 checkLoginStatusAsync 會呼叫)
	@PostMapping("/validate")
	public ResponseEntity<?> validateToken(
			@RequestHeader(value = "Authorization", required = false) String authHeader) {
		String token = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}

		if (token != null && jwtUtil.validateToken(token)) {
			return ResponseEntity.ok(Map.of("valid", true, "message", "Token 有效"));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("valid", false, "message", "Token 無效或已過期"));
		}
	}

	// 變更會員資料
	@PutMapping("/updatemember")
	public ResponseEntity<Member> updateMember(@RequestBody Member mem) {
		Member foundMem = memRepo.findByMemberId(mem.getMemberId());
		if (foundMem != null) {
			foundMem.setAddress(mem.getAddress());
			foundMem.setEmail(mem.getEmail());
			memRepo.save(foundMem);
			return ResponseEntity.ok(foundMem);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 變更密碼
	@PutMapping("/updatepassword")
	public ResponseEntity<?> updatePassword(@RequestParam String memid, @RequestParam String oldpw,
			@RequestParam String newpw) {
		Member foundMem = memRepo.findByMemberId(memid);
		if (foundMem != null && foundMem.getPassword().equals(oldpw)) {
			foundMem.setPassword(newpw);
			memRepo.save(foundMem);
			return ResponseEntity.ok(foundMem);
		} else {
			return ResponseEntity.badRequest().body("舊密碼錯誤");
		}
	}

	// 員工查詢會員
	@GetMapping("/searchmember")
	public List<Member> searchMember(@RequestParam(required = false) String membername,
			@RequestParam(required = false) String idnumber, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String memberid) {
		// 處理空字串轉 null
		membername = (membername != null && membername.isEmpty()) ? null : membername;
		idnumber = (idnumber != null && idnumber.isEmpty()) ? null : idnumber;
		phone = (phone != null && phone.isEmpty()) ? null : phone;
		memberid = (memberid != null && memberid.isEmpty()) ? null : memberid;

		return memRepo.search(membername, idnumber, phone, memberid);
	}
}