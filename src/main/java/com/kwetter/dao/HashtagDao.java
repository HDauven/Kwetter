package com.kwetter.dao;

import com.kwetter.model.Hashtag;

import java.util.List;

/**
 * Created by hein on 5/18/17.
 */
public interface HashtagDao extends GenericDao<Hashtag> {

    Hashtag findByTag(String tag);

}
