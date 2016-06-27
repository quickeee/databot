package com.sck.repository;


import com.sck.domain.JobLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by KINCERS on 4/27/2015.
 */
public interface JobLogRepository extends JpaSpecRepository<JobLog, Long> {

    Page<JobLog> findByJobId(Long id, Pageable pageable);
    List<JobLog> findByJobId(Long id);

    JobLog findByIdAndJobId(Long id, Long jobId);

}
