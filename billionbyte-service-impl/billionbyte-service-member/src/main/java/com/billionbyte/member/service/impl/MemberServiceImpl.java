package com.billionbyte.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.billionbyte.base.BaseResponse;
import com.billionbyte.constants.Constants;
import com.billionbyte.member.mapper.UserMapper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.Action;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.cluster.metadata.MetaDataIndexTemplateService;
import org.elasticsearch.index.query.QueryBuilders.*;
import com.billionbyte.pojo.UserDo;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Mapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.query.*;
import org.elasticsearch.*;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.RandomScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.significant.InternalSignificantTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.ParsedSingleValueNumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.billionbyte.base.BaseApiService;
import com.billionbyte.base.BaseResponse;
import com.billionbyte.member.service.MemberService;
import springfox.documentation.spring.web.json.Json;

import javax.xml.ws.soap.MTOM;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestHighLevelClient highLevelClient;


    @Override
    public String member() {
        return "ok";
    }

    @Override
    public int insertMember(UserDo userDo) {

     return userMapper.egister(userDo);

    }

    @Override
    public int updateMember(String userPassword,Long userId) {

        return  userMapper.updateUserPassword(userPassword,userId);
    }

    @Override
    public UserDo getMember(Long userId) {
        return userMapper.findByUserId(userId);
    }

    @Override
    public int deleteMember(Long userId) {
        return   userMapper.deleteUserByUserId(userId);
    }

    @Override
    public List<UserDo> searchByNameMember(String userName) throws IOException {
//        //闭区间查询
//        QueryBuilder queryBuilder0 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2");
//        //开区间查询
//        QueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2")
//                .includeUpper(false).includeLower(false);//默认是true，也就是包含
//        //大于
//        QueryBuilder queryBuilder2 = QueryBuilders.rangeQuery("fieldName").gt("fieldValue");
//        //大于等于
//        QueryBuilder queryBuilder3 = QueryBuilders.rangeQuery("fieldName").gte("fieldValue");
//        //小于
//        QueryBuilder queryBuilder4 = QueryBuilders.rangeQuery("fieldName").lt("fieldValue");
//        //小于等于
//        QueryBuilder queryBuilder5 = QueryBuilders.rangeQuery("fieldName").lte("fieldValue");
          //通过id删除文档
//        DeleteRequest deleteRequest = new DeleteRequest(indexName); client.indices().create(deleteRequest);
//        通过id更新文档
//        UpdateRequest updateRequest=new UpdateRequest();
//        updateRequest.index("").type("").id("").doc("jsonObject");

////不分词查询 汉字只能查询一个字，英语是一个单词.，查询结果与分词器特性有关系，所以关键字必须匹配倒排索引
//        QueryBuilder queryBuilder=QueryBuilders.termQuery("fieldName", "fieldlValue");
////分词查询，采用默认的分词器，将关键字分词后匹配倒排索引
//       QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("fieldName", "fieldlValue");
//短语精确匹配        QueryBuilders.matchPhraseQuery("field","Str")
  //使用muiltquery进行多字段查找匹配      QueryBuilders.matchPhraseQuery("field","Str")
        //高亮处理字段
//       new  SearchSourceBuilder().highlighter(new HighlightBuilder().field("highlightField"));

        //bool （布尔）过滤器，是个复合过滤器（compound filter），它可以接受多个其他过滤器作为参数，并将这些过滤器结合成各式各样的布尔（逻辑）组合，由三部分组成：
        //must：所有的语句都 必须（must） 匹配，与 AND 等价。
        //must_not：所有的语句都 不能（must not） 匹配，与 NOT 等价。
        //should ：至少有一个语句要匹配，与 OR 等价。（提高权重）
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.must(QueryBuilders.matchQuery("field","Str"));
//        boolQuery.should(QueryBuilders.matchQuery("field","Str"));
//        boolQuery.mustNot(QueryBuilders.matchQuery("field","Str"));


//
////constant_score 查询，不关心词项在文档中出现是否频繁，关心的只是它是否曾出现过
////可以包含查询或过滤，为任意一个匹配的文档指定评分,默认评分1
//        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
//        boolQueryBuilder.should(QueryBuilders.constantScoreQuery(
//                QueryBuilders.matchQuery("matchfield","Str1")));//默认评分为1
//        boolQueryBuilder.should(QueryBuilders.constantScoreQuery(
//                QueryBuilders.matchQuery("matchfield","Str2")).boost(2f));//指定评分为2
////founction_score
////用来控制评分过程
////为每个与主查询匹配的文档应用一个函数， 以达到改变甚至完全替换原始查询评分 _score 的目的
////weigth函数:为每个文档应用一个简单而不被规范化的权重提升值：当 weight 为 2 时，最终结果为 2 * _score ，
//// field_value_factor函数:修改 _score ，如将 popularity 或 votes （受欢迎或赞）作为考虑因素
////script_score 自定义脚本可以完全控制评分计算，实现所需逻辑
//        FieldValueFactorFunctionBuilder votes = ScoreFunctionBuilders
//                .scriptFunction()
//                .setWeight(2)
//                .fieldValueFactorFunction("votes")
//                .modifier(FieldValueFactorFunction.Modifier.LOG1P)
//                .factor(2);
////文档的votes字段都必须有值供 function_score计算。new_score = old_score * number_of_votes
//// 若文档的 votes 字段均无值，那么就必须使用 missing 属性提供的默认值来进行评分计算
////用modifier平滑votes的值, new_score = old_score * log(1 + number_of_votes)
//        //双倍效果。new_score = old_score * log(1 + 2 * number_of_votes)
//        boolQueryBuilder.should(QueryBuilders
//                .functionScoreQuery(QueryBuilders.multiMatchQuery("textStr","filed1","filed2"),votes)
//                .boostMode(CombineFunction.SUM));
////function_score 查询将主查询和函数包括在内。
//// field_value_factor 函数会被应用到每个与主 query 匹配的文档
////参数 boost_mode 来控制函数与查询评分 _score 合并后的结果,new_score = old_score + log(1 + 2 * number_of_votes)
//
//       //示例，多加入一个random函数
//        TermQueryBuilder terms = QueryBuilders.termQuery("fieldName","termStr");
//        RandomScoreFunctionBuilder random = ScoreFunctionBuilders.randomFunction().seed("Str").setWeight(2);
//        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders =
//                new FunctionScoreQueryBuilder
//                        .FilterFunctionBuilder[]{new FunctionScoreQueryBuilder.FilterFunctionBuilder(votes),
//                                                 new FunctionScoreQueryBuilder.FilterFunctionBuilder(random)};//多个函数
//        boolQueryBuilder.should(QueryBuilders.functionScoreQuery(filterFunctionBuilders));
//
//加上slop参数
//        new SearchSourceBuilder().query(QueryBuilders.matchPhraseQuery("matchfiels","matchStr").slop(3));
////邻近度提高相关性，使用bool查询，将多个查询的分数累计起来
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
////must子句：结果集中必须包含短语，参数minimumShouldMatch，用于去除长尾
//        boolQueryBuilder.must(QueryBuilders.matchQuery("matchfield","matchStr").minimumShouldMatch("30%"));
////使用should子句增加匹配到文档的相关评分
//        boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("matchfield","matchStr").slop(50));
////使用boost参数，让一个查询语句比其他语句更重要
////没有设置 boost 的查询语句的值为 1
//        boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("matchfield","matchStr").boost(2f));
////  部分匹配、前缀匹配、通配符、正则表达式
////  prefix 查询是一个词级别的底层的查询，它不会在搜索之前分析查询字符串，默认状态下， prefix 查询不做相关度评分计算，它只是将所有匹配的文档返回，并为每条结果赋予评分值。
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
////前缀索引,查询时的输入即搜索
//        searchSourceBuilder.query(QueryBuilders.prefixQuery("matchfield","prefixStr"));
////通配符 ，用? 匹配任意字符， * 匹配 0 或多个字符
//        searchSourceBuilder.query(QueryBuilders.wildcardQuery("matchfield","S?t*r"));
////正则表达式,以 S开头，紧跟 0 至 9 之间的任何一个数字，然后接一或多个其他字符
//        searchSourceBuilder.query(QueryBuilders.regexpQuery("matchfield","S[0-9].+"));
////短语匹配的输入即搜索，//可加上slop参数，增加词序位置的灵活
//        searchSourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery("matchfield","prefix Str").slop(10));//将查询字符串的最后一个词作为前缀使用，可看成prefix Str*
//
//
////使用前缀的风险，即 prefix 查询存在严重的资源消耗问题，短语查询的这种方式也同样如此.
////通过设置 max_expansions 参数来限制前缀扩展的影响，参数 max_expansions 控制着可以与前缀匹配的词的数量，
//// 它会先查找第一个与前缀 Str 匹配的词，然后依次查找搜集与之匹配的词（按字母顺序），
//// 直到没有更多可匹配的词或当数量超过 max_expansions 时结束
//        searchSourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery("matchfield","prefix Str")
//                .slop(10)
//                .maxExpansions(50));


/** 查询聚合：查询的结果中使用聚合
   *全局桶：不受查询结果的限制
 */
//        searchSourceBuilder.query(QueryBuilders.matchQuery("make","ford"));
////对查询结果求平均
//        AggregationBuilder singleAvgPrice = AggregationBuilders.avg("singleAvgPrice").field("price");
////创建全局桶
//        AggregationBuilder all = AggregationBuilders.global("all");
////对所有求平均
//        all.subAggregation(AggregationBuilders.avg("allPriceAvg").field("price"));
//
//        searchSourceBuilder.aggregation(singleAvgPrice);
//        searchSourceBuilder.aggregation(all);
//        searchRequest.source(searchSourceBuilder);

/**
 * 过滤聚合：过滤后对结果进行聚合
 */
////过滤
//        searchSourceBuilder.query(QueryBuilders.constantScoreQuery(QueryBuilders.rangeQuery("price").gte("10000")));
////对过滤结果求平均
//        AggregationBuilder singleAvgPrice = AggregationBuilders.avg("singleAvgPrice").field("price");

/**
 * 查询
 * 过滤桶：文档满足过滤桶的条件时，将其加入到桶内
 * 使用过滤桶在查询范围基础上应用过滤器
 */
////查询
//        searchSourceBuilder.query(QueryBuilders.matchQuery("make","ford"));
////创建过滤桶
//        AggregationBuilder recent_sales = AggregationBuilders.filter("recent_sales",
//                QueryBuilders.rangeQuery("sold").from("2020-02-14").to("2020-02-15"));
////对桶内文档聚合
//        AggregationBuilder avgPrice = AggregationBuilders.avg("price").field("price");
//        searchSourceBuilder.aggregation(recent_sales);
//        searchSourceBuilder.aggregation(avgPrice);
//        searchRequest.source(searchSourceBuilder);
        //初始化查询请求，设置索引，type
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("billionbyte");
        searchRequest.types("member");
        // 关键字匹配
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("userName", userName);
        //第几页开始
        int from = 0;
        //第几条
        int size = 200;
        //结果总数
        long total = 0;
        List<UserDo> list=new ArrayList<>();
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //模糊匹配
         matchQuery.fuzziness(Fuzziness.AUTO);
         QueryBuilder totalFilter = QueryBuilders.boolQuery().filter(matchQuery);
        //设置参数
         sourceBuilder.query(totalFilter).from(from).size(size);
         //设置超时时间
         sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
                //排序，按匹配度从高到低
                sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
                searchRequest.source(sourceBuilder);
                //发起查询
                SearchResponse response = highLevelClient.search(searchRequest);
                //处理返回结果
                SearchHit[] hits = response.getHits().getHits();
                for (SearchHit hit : hits) {
                    System.out.println(hit);
                    //转为json格式
                    JSONObject json=JSONObject.parseObject(hit.getSourceAsString());
                    //再由json转为实体类
                    System.out.println(json.toJSONString());
                    list.add ((UserDo)JSONObject.toJavaObject(json,UserDo.class));
                }
                //总结果数
                total = response.getHits().totalHits;

        return  list ;
    }

    @Override
    public String countGroupByuserIdandSex() throws IOException {
        //聚合查询
        //Buckets(桶)：满足某个条件的文档集合。
        //Metrics(指标)：为某个桶中的文档计算得到的统计信息。
        //示例，用户有id,性别 按性别分组，统计每个性别每个id范围的人数。
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //将size设置为0，返回搜索结果为0，只返回聚合的结果
        sourceBuilder.size(0);
        //按id大小分组，分为1-8 和9-10 两组，
        AggregationBuilder userId = AggregationBuilders.filters("range1_userId",
                new FiltersAggregator.KeyedFilter("range1-8",QueryBuilders.rangeQuery("userId").from(1).to(8)),
               new FiltersAggregator.KeyedFilter("range9-10",QueryBuilders.rangeQuery("userId").from(9).to(10)));
//        userId.subAggregation(AggregationBuilders.range("range0-4_userId").field("userId").addRange(1,2));//
//        userId.subAggregation(AggregationBuilders.range("range6-10_userId").field("userId").addRange(9,10));
        //按性别分组，1为男 0为女
        AggregationBuilder sex = AggregationBuilders.terms("sex").field("sex.keyword");//.keyword
        //(每个性别的用户再按照电话号码分类，
        AggregationBuilder  mobile = AggregationBuilders.terms("mobile").field("mobile.keyword");//keyword
//

//        mobile.subAggregation(userId);
//        userId.subAggregation(mobile);
        //桶嵌套,先按性别分组统计，再按电话号码的range分组统计，统计每组记录数。
        sex.subAggregation(userId);
        sourceBuilder.aggregation(sex);

                //初始化Request
                SearchRequest searchRequest=new SearchRequest();
                searchRequest.source(sourceBuilder);
                // 发起查询
                SearchResponse response = highLevelClient.search(searchRequest);
                //处理返回结果
                System.out.println(response);
//                String result="";
//                Terms agg=response.getAggregations().get("sex");
//                 for(Terms.Bucket sexbucket :agg.getBuckets()) {
//                     String key = sexbucket.getKeyAsString();            // sex的分组
//                     long docCount = sexbucket.getDocCount();
//                     result+=" {"+agg.getName()+":"+key+","+"docCount:"+docCount;
//
//                     Filters range_agg = sexbucket.getAggregations().get("range1_userId");
//                     // For each entry
//                     for (Filters.Bucket rangebuckt : range_agg.getBuckets()) {
//                         String rangekey = rangebuckt.getKeyAsString();            //按id范围的分组名
//                         long rangedocCount = rangebuckt.getDocCount();            // Doc count 记录数
//                         result+=" {"+range_agg.getName()+":"+rangekey+","+"docCount:"+rangedocCount+"} ";
//
//                     }
//                     result+="} ";
//                 }
        //手动拼接很麻烦，优化为使用下面的方法
            //    String result=getAggregationResult(response,response.getClass());

        return   response.toString();
    }
    @Override
    public String avgUserIdGroupBySex() throws IOException{
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //将size设置为0，返回搜索结果为0，只返回聚合的结果
        sourceBuilder.size(0);
        //按性别分组，计算每个性别的平均userid.
        AggregationBuilder teamAgg= AggregationBuilders.terms("sex").field("sex.keyword");
        teamAgg.subAggregation(AggregationBuilders.avg("avg_userId").field("userId"));
        sourceBuilder.aggregation(teamAgg);
        //初始化Request
        SearchRequest searchRequest=new SearchRequest();
        searchRequest.source(sourceBuilder);
        // 发起查询
        SearchResponse response = highLevelClient.search(searchRequest);
//        Terms agg = response.getAggregations().get("sex");
//        String avg_userIdGroupBySex="";
//        for(Terms.Bucket bucket :agg.getBuckets()){//遍历所有分组
//            String key = bucket.getKeyAsString();                  // bucket key 为sex
//            long docCount = bucket.getDocCount();            // Doc count 当前sex的记录数量
//
//            Map subaggmap = bucket.getAggregations().asMap();//拿到子桶
//            avg_userIdGroupBySex+="{sex: "+key+",avg_userId: "+((Avg)subaggmap.get("avg_userId")).getValue()+"}";
//        }
//        JSONObject.toJSONString(getAggregationMap(response,response.getClass()));
        return response.toString();
    }
    @Override
    public String insertMemeberToES(UserDo userDo) throws IOException {
        IndexRequest indexRequest=new IndexRequest();
        indexRequest.index("billionbyte").type("member").id(userDo.getUserId()+"");


        indexRequest.source(toMap(userDo));
        IndexResponse indexResponse=highLevelClient.index(indexRequest);
        System.out.println(indexResponse);
            return     indexResponse.toString();
    }

    @Override
    public String updateMemberToES(UserDo userDo) throws IOException {
       //       通过id更新文档
        UpdateRequest updateRequest=new UpdateRequest();

        updateRequest.index("billionbyte").type("member").id(userDo.getUserId()+"").doc(toMap(userDo));
        UpdateResponse response=highLevelClient.update(updateRequest);

        return response.toString();
    }

    @Override
    public List<UserDo> boolSearchByAnyField(UserDo userDo) throws IOException {
        SearchRequest request=new SearchRequest();
        request.indices("billionbyte").types("member");
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        Map<String,Object> map=toMap(userDo);
        for(Map.Entry<String,Object> entry : map.entrySet()){
            if(entry.getValue()!=null && !entry.getValue().equals("") && !entry.getValue().equals("\u0000") ){
                boolQuery.must(QueryBuilders.matchQuery(entry.getKey(),entry.getValue()));
            }
        }
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        sourceBuilder.query(boolQuery).size(99);
        request.source(sourceBuilder);
        SearchResponse response=highLevelClient.search(request);
        //处理结果返回为list<user>
        List<UserDo> list=new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit);
            //转为json格式
            JSONObject json=JSONObject.parseObject(hit.getSourceAsString());
            //再由json转为实体类
            System.out.println(json.toJSONString());
            list.add ((UserDo)JSONObject.toJavaObject(json,UserDo.class));
        }

        return list;
    }

//    @Override
//    public String deleteAllMemberToES() throws IOException {
//        DeleteRequest deleteRequest = new DeleteRequest("billionbyte", "member", );
//
//          DeleteResponse response=  highLevelClient.delete(deleteRequest);
//
//        return response.toString() ;
//
//    }

    /**
     * 使用递归+反射 遍历所有指标聚合和桶聚合，
     **/
    public String getAggregationResult(Object object,Class tClass){
         String result="";
         try {
             //使用反射调用方法取得聚合结果的Map集合
             Method method=tClass.getDeclaredMethod("getAggregations");
             Aggregations agg= (Aggregations)method.invoke(object);
            for(Map.Entry<String,Aggregation> entry : agg.asMap().entrySet()) {
                if (entry == null) {
                    return "";
                }
                String aggName = entry.getKey();
                result += "{" + aggName + ":";
                //必须先判断此聚合是指标聚合还是桶聚合。
                boolean isMetric = entry.getValue().getClass().getName().contains("metrics");
                if (isMetric) {//使用指标聚合遍历

                    ParsedSingleValueNumericMetricsAggregation aggInstance=(ParsedSingleValueNumericMetricsAggregation)entry.getValue();
                    //指标聚合不会再有子聚合，所以可以直接遍历取出结果
                    result+=aggInstance.getValueAsString();
                    result += "}";
                }
                if (!isMetric){//使用桶聚合遍历
                    MultiBucketsAggregation aggInstance = (MultiBucketsAggregation) entry.getValue();
                for (MultiBucketsAggregation.Bucket bucket : aggInstance.getBuckets()) {
                    String key = bucket.getKeyAsString();  //具体的分组
                    long docCount = bucket.getDocCount();  //此分组下的文档数
                    //该桶聚合下可能还有子聚合，所以递归遍历当前桶下的所有子聚合
                    result += " {" + aggInstance.getName() + ":" + key + "," + "docCount:" + docCount + "," +
                            getAggregationResult(bucket, MultiBucketsAggregation.Bucket.class) + "}";
                }
                result += "}";
                }
            }
         }catch (Exception e){
             e.printStackTrace();
         }

        return result ;
    }
    public Map<String,Object> getAggregationMap(Object object,Class tClass){
        Map<String,Object> map=new HashMap<>();
        try {
            //使用反射调用方法取得聚合结果的Map集合
            Method method=tClass.getDeclaredMethod("getAggregations");
            Aggregations agg= (Aggregations)method.invoke(object);
            for(Map.Entry<String,Aggregation> entry : agg.asMap().entrySet()) {
                if (entry == null) {
                    return null;
                }
                String aggName = entry.getKey();

                //必须先判断此聚合是指标聚合还是桶聚合。
                boolean isMetric = entry.getValue().getClass().getName().contains("metrics");
                if (isMetric) {//使用指标聚合遍历

                    ParsedSingleValueNumericMetricsAggregation aggInstance=(ParsedSingleValueNumericMetricsAggregation)entry.getValue();
                    //指标聚合不会再有子聚合，所以可以直接遍历取出结果
//                    result+=aggInstance.getValueAsString();
//                    result += "}";
                    map.put(aggName,aggInstance.getValueAsString());
                    System.out.println(aggName+aggInstance.getValueAsString());
                }
                if (!isMetric){//使用桶聚合遍历
                    MultiBucketsAggregation aggInstance = (MultiBucketsAggregation) entry.getValue();
                    for (MultiBucketsAggregation.Bucket bucket : aggInstance.getBuckets()) {
                        String key = bucket.getKeyAsString();  //具体的分组
                        long docCount = bucket.getDocCount();  //此分组下的文档数
                        //该桶聚合下可能还有子聚合，所以递归遍历当前桶下的所有子聚合
//                        result += " {" + aggInstance.getName() + ":" + key + "," + "docCount:" + docCount + "," +
//                                getAggregationResult(bucket, MultiBucketsAggregation.Bucket.class) + "}";
                        map.put(key,getAggregationMap(bucket, MultiBucketsAggregation.Bucket.class));
                    }
//                    result += "}";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return map ;
    }
    /**
     * UserDo转为Map，
     **/
    public Map<String,Object> toMap(UserDo userDo){

        Map<String, Object> source = new HashMap<String, Object>();
        source.put("userId",userDo.getUserId());
        source.put("userName",userDo.getUserName());
        source.put("email",userDo.getEmail());
        source.put("mobile",userDo.getMobile());
        source.put("password",userDo.getPassword());
        source.put("sex",String.valueOf(userDo.getSex()));
        return source;
    }
}
