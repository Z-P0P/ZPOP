package com.zpop.web.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zpop.web.dto.admin.AdminReportedCommentDto;
import com.zpop.web.dto.admin.AdminReportedMeetingDto;
import com.zpop.web.dto.admin.AdminReportedMemberDto;
import com.zpop.web.service.admin.AdminReportService;

@RequestMapping("/api/admin/report")
@RestController("adminReportController")
public class ReportController {

	private final AdminReportService adminReportService;

	@Autowired
	public ReportController(AdminReportService adminReportService) {
		this.adminReportService = adminReportService;
	}

	@GetMapping()
	public String member() {
		return "redirect:report/member?p=1";
	}

	@GetMapping("member")
	public Map<String,Object> getReportedMemberList(
		@RequestParam(name="page", defaultValue="1") int page
		,@RequestParam @Nullable String keyword
		,@RequestParam @Nullable Integer period
		,@RequestParam @Nullable @DateTimeFormat(iso=ISO.DATE) Date minDate
		,@RequestParam (name="option", defaultValue = "nickname") String option
		,@RequestParam(name="num", defaultValue="10") Integer num
		,@RequestParam(name="order", defaultValue="desc") String order) {
		List<AdminReportedMemberDto> reportedMembers = adminReportService.getReportedMembers (page, keyword, option, minDate, period, num, order);
		int count = adminReportService.countReportedMembers(keyword, option, minDate, period);
		Map<String,Object> result = new HashMap<>();
		result.put("reportedMembers",reportedMembers);
		result.put("count", count);
		
		return result;
	}

	@GetMapping("meeting")
	public Map<String,Object> getReportedMeetingList(
	@RequestParam(name="page", defaultValue="1") int page
	,@RequestParam @Nullable String keyword
	,@RequestParam @Nullable Integer period
	,@RequestParam @Nullable @DateTimeFormat(iso=ISO.DATE) Date minDate
	,@RequestParam (name="option", defaultValue = "title") String option
	,@RequestParam(name="num", defaultValue="10") Integer num
	,@RequestParam(name="order", defaultValue="desc") String order) {

		List<AdminReportedMeetingDto> reportedMeetings = adminReportService.getReportedMeetings(page, keyword, option, minDate, period,num,order);
		int count = adminReportService.countReportedMeetings(keyword, option, minDate, period);
		Map<String,Object> result = new HashMap<>();
		result.put("reportedMeetings",reportedMeetings);
		result.put("count", count);

		return result;
	}

	@GetMapping("comment")
	public Map<String,Object> getReportedCommentList(
		@RequestParam(name="page", defaultValue="1") int page
		,@RequestParam @Nullable String keyword
		,@RequestParam @Nullable Integer period
		,@RequestParam @Nullable @DateTimeFormat(iso=ISO.DATE) Date minDate
		,@RequestParam (name="option", defaultValue = "original") String option
		,@RequestParam(name="num", defaultValue="10") Integer num
		,@RequestParam(name="order", defaultValue="desc") String order) {
		List<AdminReportedCommentDto> reportedComments = adminReportService.getReportedComments(page, keyword, option, minDate, period, num,order);
		int count = adminReportService.countReportedComments(keyword, option, minDate, period);
		Map<String,Object> result = new HashMap<>();
		result.put("reportedComments",reportedComments);
		result.put("count", count);

		return result;
	}

	@DeleteMapping("member")
	public int cancelMemberReport(@RequestParam List<Integer> ids){
		int result = adminReportService.cancelMemberReport(ids);
		return result;
	}

	@DeleteMapping("meeting")
	public int cancelMeetingReport(@RequestParam List<Integer> ids){
		int result = adminReportService.cancelMeetingReport(ids);
		return result;
	}

	@DeleteMapping("comment")
	public int cancelCommentReport(@RequestParam List<Integer> ids){
		int result = adminReportService.cancelCommentReport(ids);
		return result;
	}

	@PutMapping("member")
	public int suspendMemberReported(@RequestParam List<Integer> ids, @RequestParam Integer period){
		int result = adminReportService.suspendMemberReported(ids,period);
		return result;
	}

	@PutMapping("meeting")
	public int suspendMeetingReported(@RequestParam List<Integer> ids, @RequestParam Integer period){
		int result = adminReportService.suspendMeetingReported(ids,period);
		return result;
	}

	@PutMapping("comment")
	public int suspendCommentReported(@RequestParam List<Integer> ids, @RequestParam Integer period){
		int result = adminReportService.suspendCommentReported(ids,period);
		return result;
	}
}
