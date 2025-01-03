package io.k8screen.backend.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

@Builder
public record ServiceDTO(
    @NotNull String name,
    @NotNull String type,
    @NotNull @JsonProperty("cluster_ip") String clusterIp,
    @NotNull @JsonProperty("external_ip") String externalIp,
    @NotNull String[] ports,
    @NotNull String age) {}
