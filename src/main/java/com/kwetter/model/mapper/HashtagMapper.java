package com.kwetter.model.mapper;

import com.kwetter.model.Hashtag;
import com.kwetter.rest.dto.HashtagDto;

/**
 * Created by hein on 5/21/17.
 */
public class HashtagMapper extends GenericMapper<Hashtag, HashtagDto> {
    @Override
    public HashtagDto to(Hashtag hashtag) {
        HashtagDto hashtagDto = new HashtagDto();
        hashtagDto.setId(hashtag.getId());
        hashtagDto.setTag(hashtag.getTag());
        hashtagDto.setOccurrences(hashtag.getOccurrences());
        return hashtagDto;
    }
}
