package com.zpop.web.meeting.repository;

import com.zpop.web.meeting.entity.Meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    @Modifying
    @Query("update Meeting m set m.viewCount = m.viewCount + 1 WHERE m.id = :id")
    void increaseViewCount(int id);

}
