package com.airconsole.wm.schedule.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RuntimeInformation {
    private int availableProcessors;
    private long freeMemory;
    private long maxMemory;
    private long totalMemory;
    private int totalRoom;
    private int totalUser;
}
