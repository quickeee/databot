package com.sck.repository;

import com.sck.domain.Job;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by KINCERS on 4/27/2015.
 */
public interface JobRepository extends JpaSpecRepository<Job, Long> {

    List<Job> findByTitleStartsWithIgnoreCase(@Param("title") String title);
}
