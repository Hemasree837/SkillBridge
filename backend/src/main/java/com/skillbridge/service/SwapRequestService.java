package com.skillbridge.service;

import com.skillbridge.dto.SwapRequestDTO;
import com.skillbridge.entity.SwapRequest;
import com.skillbridge.entity.User;
import com.skillbridge.exception.BadRequestException;
import com.skillbridge.exception.ResourceNotFoundException;
import com.skillbridge.repository.SwapRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Contains all business logic for sending and responding to skill swap
 * requests between users.
 */
@Service
public class SwapRequestService {

    @Autowired
    private SwapRequestRepository swapRequestRepository;

    @Autowired
    private UserService userService;

    public SwapRequest sendRequest(SwapRequestDTO dto) {
        if (dto.getSenderId().equals(dto.getReceiverId())) {
            throw new BadRequestException("You cannot send a swap request to yourself");
        }

        User sender = userService.findUserOrThrow(dto.getSenderId());
        User receiver = userService.findUserOrThrow(dto.getReceiverId());

        SwapRequest request = new SwapRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setMessage(dto.getMessage());
        request.setStatus(SwapRequest.RequestStatus.PENDING);

        return swapRequestRepository.save(request);
    }

    public List<SwapRequest> getAllRequests() {
        return swapRequestRepository.findAll();
    }

    public List<SwapRequest> getSentRequests(Long userId) {
        return swapRequestRepository.findBySenderId(userId);
    }

    public List<SwapRequest> getReceivedRequests(Long userId) {
        return swapRequestRepository.findByReceiverId(userId);
    }

    public SwapRequest acceptRequest(Long requestId) {
        SwapRequest request = findRequestOrThrow(requestId);
        request.setStatus(SwapRequest.RequestStatus.ACCEPTED);
        return swapRequestRepository.save(request);
    }

    public SwapRequest rejectRequest(Long requestId) {
        SwapRequest request = findRequestOrThrow(requestId);
        request.setStatus(SwapRequest.RequestStatus.REJECTED);
        return swapRequestRepository.save(request);
    }

    private SwapRequest findRequestOrThrow(Long id) {
        return swapRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Swap request not found with id: " + id));
    }
}
