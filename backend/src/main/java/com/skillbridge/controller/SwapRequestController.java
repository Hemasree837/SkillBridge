package com.skillbridge.controller;

import com.skillbridge.dto.SwapRequestDTO;
import com.skillbridge.entity.SwapRequest;
import com.skillbridge.service.SwapRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles sending, accepting, rejecting, and viewing skill swap requests.
 * Base path: /api/requests
 */
@RestController
@RequestMapping("/api/requests")
public class SwapRequestController {

    @Autowired
    private SwapRequestService swapRequestService;

    @PostMapping
    public ResponseEntity<SwapRequest> sendRequest(@Valid @RequestBody SwapRequestDTO dto) {
        SwapRequest request = swapRequestService.sendRequest(dto);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    // /api/requests             -> all requests
    // /api/requests?sentBy=1    -> requests sent by user 1
    // /api/requests?receivedBy=1 -> requests received by user 1
    @GetMapping
    public ResponseEntity<List<SwapRequest>> getRequests(
            @RequestParam(required = false) Long sentBy,
            @RequestParam(required = false) Long receivedBy) {

        if (sentBy != null) {
            return ResponseEntity.ok(swapRequestService.getSentRequests(sentBy));
        }
        if (receivedBy != null) {
            return ResponseEntity.ok(swapRequestService.getReceivedRequests(receivedBy));
        }
        return ResponseEntity.ok(swapRequestService.getAllRequests());
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<SwapRequest> acceptRequest(@PathVariable Long id) {
        return ResponseEntity.ok(swapRequestService.acceptRequest(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<SwapRequest> rejectRequest(@PathVariable Long id) {
        return ResponseEntity.ok(swapRequestService.rejectRequest(id));
    }
}
