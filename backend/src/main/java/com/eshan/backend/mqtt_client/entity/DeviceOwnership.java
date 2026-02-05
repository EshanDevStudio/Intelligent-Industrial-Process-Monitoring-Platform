package com.eshan.backend.mqtt_client.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "device_ownership")
public class DeviceOwnership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String deviceId;

    @Column(nullable = false)
    private String deviceName;

    public DeviceOwnership() {
    }

    public DeviceOwnership(Long id, String userId, String deviceId, String deviceName) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
