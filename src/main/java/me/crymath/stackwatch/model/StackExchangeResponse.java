package me.crymath.stackwatch.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Generic wrapper for StackExchange API responses. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StackExchangeResponse<T> {
    private List<T> items;
    private boolean has_more;
    private int quota_max;
    private int quota_remaining;
    private Integer backoff; // Optional: seconds to wait

    // Error fields (optional)
    private Integer error_id;
    private String error_message;
    private String error_name;
}
