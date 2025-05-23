package com.saurabh.fintrackbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class APIResponse {
    private String message;
    private boolean status;
}
