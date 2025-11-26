package com.demo.model; // ⚠️ 確認 package 名稱

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Primary Key (PK)

	@Column(name = "member_id")
	private String memberId; // 業務主鍵 (Business Key, e.g., M00001)

	private String name;
	private String password;

	@Column(name = "id_number", unique = true)
	private String idNumber; // 登入或唯一性驗證

	@Column(unique = true)
	private String email; // 登入或唯一性驗證

	private String phone;
	private String address;

	@Temporal(TemporalType.DATE)
	private Date birthday;
	private String gender;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "register_date")
	@CreatedDate()
	private Date registerDate; // 註冊日期 (需開啟 Auditing)
}