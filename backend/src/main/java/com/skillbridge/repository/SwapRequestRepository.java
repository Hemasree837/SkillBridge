package com.skillbridge.repository;

import com.skillbridge.entity.SwapRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {

    List<SwapRequest> findBySenderId(Long senderId);

    List<SwapRequest> findByReceiverId(Long receiverId);

    List<SwapRequest> findByReceiverIdAndStatus(Long receiverId, SwapRequest.RequestStatus status);

    List<SwapRequest> findBySenderIdAndStatus(Long senderId, SwapRequest.RequestStatus status);
}
