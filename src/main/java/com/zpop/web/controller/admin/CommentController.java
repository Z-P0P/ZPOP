package com.zpop.web.controller.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zpop.web.dto.admin.AdminCommentDto;
import com.zpop.web.dto.admin.AdminCommentListResponse;
import com.zpop.web.service.admin.AdminCommentService;

@Controller("adminCommentController")
@RequestMapping("api/admin/comment")
@RestController
public class CommentController {

	private final AdminCommentService service;
	
	@Autowired
	public CommentController (AdminCommentService adminCommentService) {
		this.service = adminCommentService;
	}
	
	@GetMapping("list")
	public AdminCommentListResponse getList(
		@RequestParam(name="page", defaultValue="1") int page
		,@RequestParam @Nullable String keyword
		,@RequestParam @Nullable Integer period
		,@RequestParam @Nullable @DateTimeFormat(iso=ISO.DATE) Date minDate
		,@RequestParam (name="option", defaultValue = "nickname") String option
		,@RequestParam(name="num", defaultValue="10") Integer num
		,@RequestParam(name="order", defaultValue="desc") String order) {
		
		List<AdminCommentDto> comments = service.getList(page, keyword, option, minDate, period, num, order);
		int count = service.countBySearch(keyword, option, minDate, period);

		return AdminCommentListResponse
									.builder()  
									.comments(comments)
									.count(count)
									.build();

	}

	@DeleteMapping()
	public int updateDeletedAt(@RequestParam List<Integer> ids, @RequestParam boolean isDeleted){
		
		int result = service.updateDeleteAt(ids,isDeleted);
		return result;
	}
}
