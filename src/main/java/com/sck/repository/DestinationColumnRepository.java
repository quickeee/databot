package com.sck.repository;


import com.sck.domain.DestinationColumn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by KINCERS on 4/27/2015.
 */
public interface DestinationColumnRepository extends JpaSpecRepository<DestinationColumn, Long> {

    Page<DestinationColumn> findByDestinationId(Long objectId, Pageable pageable);
    List<DestinationColumn> findByDestinationId(Long objectId);

    DestinationColumn findByIdAndDestinationId(Long id, Long objectId);

}
