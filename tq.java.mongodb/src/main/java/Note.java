public class Note {

    //TODO Mongodb ObjectId
    //TODO 确认
    //mongodb自身最快的插入速度，其实每批次1000条，因为mongodb内部对所有大于1000条的批次都是拆解成1000条一组的，
    // 但对考虑到最高性价比的插入，一般我们采用5000-10000，主要考虑的还是网络IO，银子字段比较多，
    // 这么多的字段在1000条以上的话，进行网络IO'传输，就会造成消耗大于插入，所以在这个时候，反而使用大一点的批数会有优势
    //TODO bson
    //TODO ObjectId 是否自增 生成规则 比较
    //TODO 线上删除多 的问题...
    //TODO count() == -1 的情况
    //TODO 主从切换造成的影响
        //TODO 什么时候会主从切换
        //TODO 连接断开
    //TODO mongo的bulk.
    //TODO 既然同一个字段不限制具体的数据类型, 那么索引是以哪种字段为基本的呢?
    //TODO MongoDB 磁盘空间不释放的问题
}
