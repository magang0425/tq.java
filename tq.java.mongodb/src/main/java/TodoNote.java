public class TodoNote {

    //TODO Mongodb ObjectId

    //TODO 确认
    //mongodb自身最快的插入速度，其实每批次1000条，因为mongodb内部对所有大于1000条的批次都是拆解成1000条一组的，
    // 但对考虑到最高性价比的插入，一般我们采用5000-10000，主要考虑的还是网络IO，银子字段比较多，
    // 这么多的字段在1000条以上的话，进行网络IO'传输，就会造成消耗大于插入，所以在这个时候，反而使用大一点的批数会有优势

    //TODO bson

    //TODO ObjectId 是否自增 生成规则 比较

    //TODO
}