package org.aicha.service;

import org.aicha.model.Request;
import org.aicha.model.enums.RequestStatus;
import org.aicha.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService() {
        requestRepository = new RequestRepository();
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Optional<Request> getRequestById(Long id) {
        // validate id
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be provided");
        }
        return requestRepository.findById(id);
    }

    public Optional<Request> createRequest(Request request) {
        // validate request
        if (request == null || request.getRequester() == null || request.getTask() == null || request.getType() == null) {
            throw new IllegalArgumentException("Request, requester, task and type must be provided");
        }

        return requestRepository.create(request);
    }

    public Optional<Request> updateRequest(Request request) {
        if (request == null || request.getId() == null) {
            throw new IllegalArgumentException("Request and id must be provided");
        }
        return requestRepository.update(request);
    }

    public Request deleteRequest(Request request) {
        // validate request
        if (request == null || request.getId() == null) {
            throw new IllegalArgumentException("Request and id must be provided");
        }
        return requestRepository.delete(request);
    }

    public String validateRequest(Request newRequest) {
        // Request must be provided
        if (newRequest == null)
            return "Request must be provided";

        // Request type must be provided
        if (newRequest.getType() == null)
            return "Request type must be provided";



        // Requester must be a user
        if (newRequest.getRequester() == null || newRequest.getRequester().getId() == null)
            return "Requester must be a user";

        // Task must be a task
        if (newRequest.getTask() == null || newRequest.getTask().getId() == null)
            return "Task must be a task";

        // Request can't be requested after deadline
        if (newRequest.getTask().getDeadline() != null && newRequest.getRequestedAt().toLocalDate().isAfter(newRequest.getTask().getDeadline()))
            return "Cannot request after deadline";

        // Request can't be requested by the task creator
        if (newRequest.getTask().getCreatedBy().equals(newRequest.getRequester()))
            return "Cannot request by the task creator";

        // Check Requester tokens
        if (!newRequest.getRequester().canRequest(newRequest.getType()))
            return "You don't have enough tokens";

        return null;
    }

    public Optional<Request> respondToRequest(Request request, RequestStatus status) {
        // validate request
        if (request == null || request.getId() == null) {
            throw new IllegalArgumentException("Request and id must be provided");
        }
        // validate status
        if (status == null) {
            throw new IllegalArgumentException("Status must be provided");
        }

        request.setStatus(status);
        request.setRespondedAt(LocalDateTime.now());
        return this.updateRequest(request);
    }
}
