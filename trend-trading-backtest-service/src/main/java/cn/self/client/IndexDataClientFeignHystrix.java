package cn.self.client;

import cn.hutool.core.collection.CollectionUtil;
import cn.self.pojo.IndexData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * IndexDataClientFeignHystrix 实现了 IndexDataClient，所以就提供了对应的方法，当熔断发生的时候，对应的方法就会被调用了。
 * 这里的方法就是指如果 INDEX-DATA-SERVICE 不可用或者不可访问，就会返回个 0000-00-00 出去
 */
@Component
public class IndexDataClientFeignHystrix implements IndexDataClient {
    @Override
    public List<IndexData> getIndexData(String code) {
        IndexData indexData = new IndexData();
        indexData.setDate("0000-00-0");
        indexData.setClosePoint(0);
        return CollectionUtil.toList(indexData);
    }
}
