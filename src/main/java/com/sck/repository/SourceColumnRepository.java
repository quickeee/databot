package com.sck.repository;

import com.sck.domain.SourceColumn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by KINCERS on 4/27/2015.
 */
public interface SourceColumnRepository extends JpaSpecRepository<SourceColumn, Long> {

    Page<SourceColumn> findBySourceId(Long objectId, Pageable pageable);
    List<SourceColumn> findBySourceId(Long objectId);

    SourceColumn findByIdAndSourceId(Long id, Long objectId);

}
