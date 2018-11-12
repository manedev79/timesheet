package com.github.manedev79.timesheet.adapters.primary.rest;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {
}
