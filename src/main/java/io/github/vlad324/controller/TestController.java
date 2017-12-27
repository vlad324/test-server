package io.github.vlad324.controller;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author v.radkevich
 * @since 12/27/17
 */
@RestController
public class TestController {
    private static Logger LOG = Logger.getLogger(TestController.class);

    private Queue<Long> tasks = new LinkedBlockingQueue<>(10);

    @GetMapping("/")
    public boolean addTask(@RequestParam("id") Long id) {
        return tasks.offer(id);
    }

    @Scheduled(fixedDelay = 2_000)
    public void runTask() {
        Long polled = tasks.poll();

        if (Objects.nonNull(polled)) {
            LOG.debug("Processed id: {} " + polled + ". Queue size: " + tasks.size());
        } else {
            LOG.debug("Queue is empty.");
        }
    }

}
