package com.zpop.web.service.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zpop.web.dao.CommentDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.ReportedCommentDao;
import com.zpop.web.dao.ReportedMeetingDao;
import com.zpop.web.dao.ReportedMemberDao;
import com.zpop.web.dto.admin.AdminReportedCommentDto;
import com.zpop.web.dto.admin.AdminReportedMeetingDto;
import com.zpop.web.dto.admin.AdminReportedMemberDto;
import com.zpop.web.entity.ReportedComment;
import com.zpop.web.entity.ReportedMeeting;
import com.zpop.web.entity.ReportedMember;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.meeting.Meeting;

@Service
public class DefaultAdminReportService implements AdminReportService{

	@Autowired
	private ReportedMemberDao reportedMemberDao;
	@Autowired
	private ReportedMeetingDao reportedMeetingDao;
	@Autowired
	private ReportedCommentDao reportedCommentDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MeetingDao meetingDao;
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<AdminReportedMemberDto> getReportedMembers(int page, String keyword, String option, Date minDate, Integer period, Integer num, String order) {
		int size = num;
		int offset=(page-1)*num;
		List<AdminReportedMemberDto> list = reportedMemberDao.getAdminViewList(size, offset, keyword, option, minDate, period, order);
		
		return list;
	}

	@Override
	public List<AdminReportedCommentDto> getReportedComments(int page, String keyword, String option, Date minDate, Integer period, Integer num, String order) {
		int size = num;
		int offset=(page-1)*num;
		List<AdminReportedCommentDto> list = reportedCommentDao.getAdminViewList(size, offset, keyword, option, minDate, period, order);
		
		return list;
	}

	@Override
	public List<AdminReportedMeetingDto> getReportedMeetings(int page, String keyword, String option, Date minDate, Integer period, Integer num, String order) {
		int size = num;
		int offset=(page-1)*size;
		List<AdminReportedMeetingDto> list = reportedMeetingDao.getAdminViewList(size, offset , keyword, option, minDate, period, order);
		
		return list;
	}

	@Override
	public int countReportedMembers(String keyword, String option, Date minDate, Integer period) {
		return reportedMemberDao.count(keyword, option, minDate, period);
	}

	@Override
	public int countReportedComments(String keyword, String option, Date minDate, Integer period) {
		return reportedCommentDao.count(keyword, option, minDate, period);
	}

	@Override
	public int countReportedMeetings(String keyword, String option,Date minDate, Integer period) {
		return reportedMeetingDao.count(keyword, option, minDate, period);
	}

	@Override
	public int cancelMemberReport(List<Integer> ids) {
		/* 작업순서
		 * 1. 신고id를 통해서 피신고자의 id 조회
		 * 2. member table의 isSuspened 값을 false로 변경
		 * 3. reportedMeeting table의 blockedAt, releasedAt을 null로 변경
		 */

		 List<ReportedMember> reportedMembers = reportedMemberDao.getByIds(ids);
		 List<Integer> reportedMemberIds = reportedMembers.stream()
								  .map(ReportedMember::getReportedId)
								  .distinct()
								  .collect(Collectors.toList());
  
		  Boolean isSuspended = false;
		  memberDao.updateAllIsSuspended(reportedMemberIds, isSuspended);
		  int processedAtResult = reportedMemberDao.updateAll(ids,null);
  
		  return processedAtResult;
	}

	@Transactional
	@Override
	public int cancelMeetingReport(List<Integer> ids) {

		/* 작업순서 (dto를 만들어야할지? 아니면 이렇게 해야할지? dto를 만들기에는 부담스럽고, 아래처럼 하자니 복잡하네요)
		 * 1. 신고id를 통해서 피신고자가 작성한 모임글의 id 조회
		 * 2. 모임글id를 통해서 피신고자의 id 조회
		 * 3. member table의 isSuspened 값을 false로 변경
		 * 4. meeting table의 deletedAt을 null로 변경
		 * 5. reportedMeeting table의 blockedAt, releasedAt을 null로 변경
		 */

		
		List<ReportedMeeting> reportedMeetings = reportedMeetingDao.getByIds(ids);
		List<Integer> meetingIds = reportedMeetings.stream()
								.map(ReportedMeeting::getMeetingId)
								.distinct()
								.collect(Collectors.toList());
		
		List<Meeting> meetings = meetingDao.getByIds(meetingIds);
		List<Integer> regMemberIds = meetings.stream()
								.map(Meeting::getRegMemberId)
								.distinct()
								.collect(Collectors.toList());

		Boolean isSuspended = false;
		memberDao.updateAllIsSuspended(regMemberIds, isSuspended);
		meetingDao.removeAllDeletedAt(meetingIds);
		
		
		int result = reportedMeetingDao.updateAll(ids, null);

		return result;
	}

	@Transactional
	@Override
	public int cancelCommentReport(List<Integer> ids) {

		/* 작업순서 (dto를 만들어야할지? 아니면 이렇게 해야할지? dto를 만들기에는 부담스럽고, 아래처럼 하자니 복잡하네요)
		 * 1. 신고id를 통해서 피신고자가 작성한 댓글의 id 조회
		 * 2. 댓글 id를 통해서 작성자의 id 조회
		 * 3. member table의 isSuspened 값을 false로 변경
		 * 4. comment table의 deletedAt을 null로 변경
		 * 5. reportedComment table의 blockedAt, releasedAt을 null로 변경
		 */
		List<ReportedComment> reportedComments = reportedCommentDao.getByIds(ids);
		List<Integer> commentIds = reportedComments.stream()
									.map(ReportedComment::getCommentId)
									.distinct()
									.collect(Collectors.toList());

		List<Comment> comments = commentDao.getByIds(commentIds);
		List<Integer> writerIds = comments.stream()
									.map(Comment::getWriterId)
									.distinct()
									.collect(Collectors.toList());


		Boolean isSuspended = false;
		memberDao.updateAllIsSuspended(writerIds, isSuspended);
		commentDao.removeAllDeletedAt(commentIds);
		
		int result = reportedCommentDao.updateAll(ids,null);
		
		return result;
	}

	@Transactional
	@Override
	public int suspendMemberReported(List<Integer> ids, Integer period) {
		
		/* 작업순서
		 * 1. 신고id를 통해서 피신고자의 id 조회
		 * 2. member table의 isSuspened 값을 true로 변경
		 * 3. reportedMeeting table의 blockedAt, releasedAt을 각각 
		 * 	  current_timestamp와 #{releasedAt} 으로 변경
		 */
		
		Date releasedAt = getReleasedAtFromNow(period);

		List<ReportedMember> reportedMembers = reportedMemberDao.getByIds(ids);
		List<Integer> reportedMemberIds = reportedMembers.stream()
								 .map(ReportedMember::getReportedId)
								 .distinct()
								 .collect(Collectors.toList());
 
		 Boolean isSuspended = true;
		 memberDao.updateAllIsSuspended(reportedMemberIds, isSuspended);
		 int processedAtResult = reportedMemberDao.updateAll(ids,releasedAt);
 
		 return processedAtResult;

	}

	@Transactional
	@Override
	public int suspendMeetingReported(List<Integer> ids, Integer period) {

		//날짜 더하는 방법은 다음을 참조
		//https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date
		

		/* 작업순서
		 * 1. 신고id를 통해서 피신고자가 작성한 모임글의 id 조회
		 * 2. 모임글id를 통해서 피신고자의 id 조회
		 * 3. member table의 isSuspened 값을 true로 변경
		 * 4. meeting table의 deletedAt을 current_timestamp 로 변경
		 * 5. reportedMeeting table의 blockedAt, releasedAt을 각각 
		 * 	  current_timestamp와 #{releasedAt} 으로 변경
		 */
		Date releasedAt = getReleasedAtFromNow(period);

		List<ReportedMeeting> reportedMeetings = reportedMeetingDao.getByIds(ids);
		List<Integer> meetingIds = reportedMeetings.stream()
								.map(ReportedMeeting::getMeetingId)
								.distinct()
								.collect(Collectors.toList());
		
		List<Meeting> meetings = meetingDao.getByIds(meetingIds);
		List<Integer> regMemberIds = meetings.stream()
								.map(Meeting::getRegMemberId)
								.distinct()
								.collect(Collectors.toList());

		Boolean isSuspended = true;
		memberDao.updateAllIsSuspended(regMemberIds, isSuspended);
		meetingDao.updateAllDeletedAt(meetingIds);
		int processedAtResult = reportedMeetingDao.updateAll(ids,releasedAt);


		return processedAtResult;
	}

	@Transactional
	@Override
	public int suspendCommentReported(List<Integer> ids, Integer period) {
		
		/* 작업순서
		 * 1. 신고id를 통해서 피신고자가 작성한 댓글의 id 조회
		 * 2. 댓글 id를 통해서 작성자의 id 조회
		 * 3. member table의 isSuspened 값을 true로 변경
		 * 4. comment table의 deletedAt을 current_timestamp로 변경
		 * 5. reportedComment table의 blockedAt, releasedAt을 각각 
		 * 	  current_timestamp와 #{releasedAt} 으로 변경
		 */
		Date releasedAt = getReleasedAtFromNow(period);

		List<ReportedComment> reportedComments = reportedCommentDao.getByIds(ids);
		List<Integer> commentIds = reportedComments.stream()
									.map(ReportedComment::getCommentId)
									.distinct()
									.collect(Collectors.toList());

		List<Comment> comments = commentDao.getByIds(commentIds);
		List<Integer> writerIds = comments.stream()
									.map(Comment::getWriterId)
									.distinct()
									.collect(Collectors.toList());

		Boolean isSuspended = true;
		memberDao.updateAllIsSuspended(writerIds, isSuspended);
		commentDao.updateAllDeletedAt(commentIds);

		int processedAtResult = reportedCommentDao.updateAll(ids,releasedAt);

		return processedAtResult;
	}

	private static Date getReleasedAtFromNow(Integer period){
		Date releasedAt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(releasedAt);
		calendar.add(Calendar.DATE, period);
		releasedAt = calendar.getTime();
		return releasedAt;
	}
}
