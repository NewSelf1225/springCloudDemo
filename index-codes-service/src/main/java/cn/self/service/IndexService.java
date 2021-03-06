package cn.self.service;

import cn.hutool.core.collection.CollUtil;
import cn.self.pojo.Index;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {

    private List<Index> indexes;

    @Cacheable(key = "'all_codes'")
    public List<Index> get(){
        Index index = new Index();
        index.setCode("000000");
        index.setName("无效代码指数");
        return CollUtil.toList(index);
    }
}
