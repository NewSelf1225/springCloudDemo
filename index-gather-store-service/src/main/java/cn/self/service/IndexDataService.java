package cn.self.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.self.pojo.IndexData;
import cn.self.util.SpringContextUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "index_datas")
public class IndexDataService {
    private Map<String, List<IndexData>> indexDatas = new HashMap<>();

    @Autowired
    RestTemplate restTemplate;

    /**
     * 设置熔断器策略，
     *
     * @param code
     * @return
     */
    @HystrixCommand(fallbackMethod = "third_part_not_connected")
    public List<IndexData> fresh(String code) {
        List<IndexData> indexeDatas = fetch_indexes_from_third_part(code);
        indexDatas.put(code, indexeDatas);

        System.out.println("code:" + code);
        System.out.println("indexeDatas:" + indexDatas.get(code).size());

        IndexDataService indexDataService = SpringContextUtil.getBean(IndexDataService.class);
        indexDataService.remove(code);
        return indexDataService.store(code);

    }

    /**
     * 用于更新缓存数据
     */
    @CacheEvict(key = "'indexData-code-'+ #p0")
    public void remove(String code) {
    }

    /**
     * 用于放入缓存数据
     */
    @CachePut(key = "'indexData-code-'+ #p0")
    public List<IndexData> store(String code) {
        return indexDatas.get(code);
    }

    /**
     * 查询缓存数据
     */
    @Cacheable(key = "'indexData-code-'+ #p0")
    public List<IndexData> get(String code) {
        return CollUtil.toList();
    }

    /**
     * 获取第三方资源文件
     */
    public List<IndexData> fetch_indexes_from_third_part(String code) {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/" + code + ".json", List.class);
        return mapToIndexData(temp);
    }

    /**
     * 因为获取到的是map所以需要转换成index
     */
    private List<IndexData> mapToIndexData(List<Map> temp) {
        List<IndexData> indexDataList = new ArrayList<>();
        for (Map map : temp) {
            String date = map.get("date").toString();
            float closePoint = Convert.toFloat(map.get("closePoint"));

            IndexData indexData = new IndexData();
            indexData.setDate(date);
            indexData.setClosePoint(closePoint);
            indexDataList.add(indexData);
        }
        return indexDataList;
    }

    public List<IndexData> third_part_not_connected(String code) {
        System.out.println("IndexData断路器已启动");
        IndexData index = new IndexData();
        index.setClosePoint(0);
        index.setDate("n/a");
        return CollectionUtil.toList(index);
    }
}
