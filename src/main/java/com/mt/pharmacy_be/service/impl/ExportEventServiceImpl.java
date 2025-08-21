package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.exportDTO.ExportProgressDTO;
import com.mt.pharmacy_be.service.ExportEventService;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ExportEventServiceImpl implements ExportEventService {

    Sinks.Many<ExportProgressDTO> sink;
    ConnectableFlux<ExportProgressDTO> hotFlux;
    AtomicBoolean connected = new AtomicBoolean(false);

    /**
     * Service to handle export progress events.
     * This service uses a hot multicast flux to broadcast export progress updates to all connected clients.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This service allows clients to subscribe to export progress updates via Server-Sent Events (SSE).
     */
    public ExportEventServiceImpl() {
        this.sink = Sinks.many().multicast().directAllOrNothing();

        // Tạo heartbeat để giữ kết nối
        Flux<ExportProgressDTO> heartbeat = Flux.interval(Duration.ofSeconds(15))
                .map(tick -> ExportProgressDTO.builder()
                        .status("HEARTBEAT")
                        .message("Keeping connection alive")
                        .build());

        Flux<ExportProgressDTO> mergedFlux = Flux.merge(
                sink.asFlux(),
                heartbeat
        );

        this.hotFlux = mergedFlux.publish();

        connectHotFlux();

        log.info("ExportEventService initialized with hot multicast flux");
    }

    /**
     * Connects the hot flux to start broadcasting events.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This method ensures that the hot flux is connected only once,
     */
    private void connectHotFlux() {
        if (connected.compareAndSet(false, true)) {
            log.info("Connecting hot flux");
            hotFlux.connect();
        }
    }

    /**
     * Creates a stream of export progress updates.
     * This method returns a Flux that emits ExportProgressDTO objects to connected clients.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: Clients can subscribe to this stream to receive real-time updates on export progress.
     */
    @Override
    public Flux<ExportProgressDTO> createProgressStream() {
        log.info("New client connected to export progress stream");

        return hotFlux
                .filter(Objects::nonNull)
                .doOnCancel(() -> log.info("Client cancelled SSE connection"));
    }

    /**
     * Sends export progress updates to all connected clients.
     * This method emits an ExportProgressDTO object to the multicast sink, which is then broadcasted to all subscribers.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This method is used to send updates about the export process, such as status changes or completion notifications.
     */
    @Override
    public void sendExportProgress(ExportProgressDTO progress) {
        if (progress == null) {
            log.warn("Attempted to send null export progress");
            return;
        }

        log.info("Broadcasting export progress: {} - {}", progress.getFileName(), progress.getStatus());

        connectHotFlux();

        Sinks.EmitResult result = sink.tryEmitNext(progress);

        if (result.isFailure()) {
            log.warn("Failed to emit export progress: {}", result);
        }
    }
}
