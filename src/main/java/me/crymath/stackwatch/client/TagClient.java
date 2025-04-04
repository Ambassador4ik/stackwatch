package me.crymath.stackwatch.client;

import java.util.List;
import me.crymath.stackwatch.model.Tag;

public interface TagClient {
    List<Tag> listTags();

    Tag getTag(String tagName);
}
