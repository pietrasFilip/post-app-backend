package com.app.infrastructure.api.controller.clients;

import com.app.application.dto.client.CreateClientDto;
import com.app.application.dto.client.GetClientDto;
import com.app.application.dto.response.ResponseDto;
import com.app.application.service.post.PostService;
import com.app.application.service.post.queue_monitor.QueueMonitorStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientsController {
    private final PostService postService;

    @PostMapping("/add")
    public ResponseDto<GetClientDto> addToQueue(@RequestBody CreateClientDto createClientDto) {
        return new ResponseDto<>(postService.addToQueue(createClientDto));
    }

    @GetMapping("/queue")
    public ResponseDto<List<GetClientDto>> showQueue() {
        return new ResponseDto<>(postService.showQueue());
    }

    @GetMapping("/monitor/username/{username}")
    public ResponseDto<QueueMonitorStatistics> monitorQueueByUsername(@PathVariable String username) {
        return new ResponseDto<>(postService.monitorQueueByUsername(username));
    }

    @GetMapping("/monitor/id/{id}")
    public ResponseDto<QueueMonitorStatistics> monitorQueueById(@PathVariable String id) {
        return new ResponseDto<>(postService.monitorQueueById(id));
    }
}
